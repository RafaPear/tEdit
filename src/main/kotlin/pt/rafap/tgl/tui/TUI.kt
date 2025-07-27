package pt.rafap.tgl.tui

import pt.rafap.tgl.tui.color.Color.stylize
import pt.rafap.tgl.tui.color.ColorCode
import pt.rafap.tgl.tui.cursor.Cursor
import pt.rafap.tgl.tui.keyboard.KeyCode
import pt.rafap.tgl.tui.keyboard.RawConsoleInput
import pt.rafap.tgl.tui.tools.ESC
import pt.rafap.tgl.tui.tools.isRunningInTerminal
import kotlin.system.exitProcess

object TUI {

    var style: TuiStyle = TuiStyle() // Default style for TUI

    var lastUpdateSize = Cursor.bounds // Last known size of the terminal window

    var footerSize: Int = 1 // Footer size in lines
    var headerSize: Int = 1 // Header size in lines

    var injectedFun: (() -> Unit) = { } // Function to be injected into the TUI, can be set by the user

    private var isInjected: Boolean = false // Flag to check if the injected function has been called

    // Function to inject the injectedFun only once per update
    private var injectFunction: () -> Unit = {
        if (!isInjected) {
            isInjected = true
            injectedFun()
        }
        isInjected = false
    }

    init {
        if(!isRunningInTerminal()) exitProcess(0)
        Cursor.resetPos()
        Cursor.setPos(1, headerSize)
    }

    /**
     * Updates the TUI if the terminal size has changed.
     * @param updateFunc Function to be called after updating the TUI.
     */
    fun updateIfNeeded(updateFunc : () -> Unit = {}) {
        if (lastUpdateSize != Cursor.bounds) {
            Thread.sleep(1)
            clearAll()
            lastUpdateSize = Cursor.bounds
            updateFunc()
            injectFunction()
        }
    }

    /**
     * Prints a message to the TUI with the specified color codes.
     * If the message is too long, it will be split into multiple lines.
     * @param msg The message to print.
     * @param codes The color codes to apply to the message.
     */
    private fun doPrtStyle(msg: Any?, codes: List<ColorCode> = emptyList()) {
        val newMsg = msg.toString().chunkedSequence(Cursor.bounds.second - Cursor.getPos().x + 1)
            .map { it.stylize(codes) }
            .toList()

        for (c in newMsg) {
            if (Cursor.getPos().y == Cursor.bounds.first - footerSize) {
                Cursor.setPos(newY = footerSize + 1) // Reset cursor position if at bottom
            }
            if (Cursor.getPos().y == headerSize) {
                Cursor.setPos(newY = headerSize + 1) // Reset cursor position if at top
            }
            if (msg.toString().isEmpty()) {
                return
            }
            simplePrint(c)
            Cursor.doPrt(c)
            injectFunction()
        }
    }

    /**
     * Prints a message to the TUI with the specified color codes and a newline at the end.
     * @param msg The message to print.
     * @param codes The color codes to apply to the message.
     */
    fun println(msg: Any? = "", codes: List<ColorCode> = emptyList()) {
        doPrtStyle(msg.toString() + '\n', codes)
        updateIfNeeded()
    }

    /**
     * Prints a message to the TUI without a newline at the end.
     * @param msg The message to print.
     * @param codes The color codes to apply to the message.
     */
    fun print(msg: Any?, codes: List<ColorCode> = emptyList()) {
        doPrtStyle(msg, codes)
        updateIfNeeded()
    }

    /**
     * Prints a message to the TUI without any color codes.
     * @param msg The message to print.
     */
    private fun simplePrint(msg: Any?) {
        kotlin.io.print(msg.toString())
    }

    fun clear() {
        simplePrint("${ESC}3J") // Clear screen and reset cursor position
        updateIfNeeded()
    }

    fun clearAll() {
        simplePrint("${ESC}0J") // Clear entire screen
        //simplePrint("${ESC}1J") // Clear entire screen
        //Cursor.resetPos()
    }

    fun clearLine() {
        simplePrint("${ESC}K") // Clear current line
        Cursor.setPos(1) // Reset cursor position to current line
        injectFunction()
    }

    fun clearLineToEnd() {
        simplePrint("${ESC}0K") // Clear from cursor to end of line
    }

    fun clearLineToStart() {
        simplePrint("${ESC}1K") // Clear from start of line to cursor
    }

    fun writeFooter(msg: String, codes: List<ColorCode> = emptyList(), x: Int = 1, y: Int = Cursor.bounds.first-1) {
        Cursor.setPos(x, y)
        simplePrint(msg.stylize(codes.ifEmpty { style() }))
        clearLineToEnd()
        injectFunction()
    }

    fun writeHeader(msg: String, codes: List<ColorCode> = emptyList()) {
        Cursor.resetPos()
        simplePrint(msg.stylize(codes.ifEmpty { style() }))
        clearLineToEnd()
        injectFunction()
    }

    fun readKey(wait: Boolean = false): KeyCode =
        KeyCode(RawConsoleInput.read(wait))
}