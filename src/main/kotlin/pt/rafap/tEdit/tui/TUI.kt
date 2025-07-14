package pt.rafap.tEdit.tui

import pt.rafap.tEdit.datastore.Colors
import pt.rafap.tEdit.datastore.Colors.stylize
import pt.rafap.tEdit.datastore.Cursor
import pt.rafap.tEdit.datastore.TextBuffer
import pt.rafap.tEdit.datatype.ConfigReader
import pt.rafap.tEdit.datatype.KeyCode
import pt.rafap.tEdit.ext.RawConsoleInput
import pt.rafap.tEdit.logger.Logger
import pt.rafap.tEdit.logger.Severity
import pt.rafap.tEdit.tools.ESC
import pt.rafap.tEdit.tools.isRunningInTerminal
import java.io.File
import kotlin.system.exitProcess

object TUI {
    val config = ConfigReader(File("config/tui.properties"))

    var colorT = config["text"].toString()
    var colorB = config["background"].toString()

    var tempSize = Cursor.bounds

    private var useBold: Boolean = config["useBold"].toString().toBoolean()
    private val bold = if (useBold) Colors.BOLD else Colors.RESET_BOLD

    var useFooter: Boolean = config["useFooter"].toString().toBoolean()

    var useHeader: Boolean = config["useHeader"].toString().toBoolean()

    var footerSize: Int =
        try {
            config["footerSize"].toString().toInt()
        } catch (e: Exception) {
            1
        }

    var headerSize: Int =
        try {
            config["headerSize"].toString().toInt()
        } catch (e: Exception) {
            1
        }

    private var isInjected: Boolean = false

    var buffered: Boolean = true
        get() = !isInjected || field

    var injectedFunctionExt: (() -> Unit) = {
        val prevFun = Logger.printFun
        Logger.printFun = { message, codes ->
            writeHeader(message, codes)
        }
        // print debug stats as cursor pos, bounds, etc.
        Logger.log("Cursor Position: ${Cursor.getPos()} | Bounds: ${Cursor.bounds} | Logger Severity: ${Logger.severity.name}",
            Severity.DEBUG)
        Logger.printFun = prevFun
    }

    private var injectFunction: () -> Unit = {
        if (!isInjected) {
            isInjected = true
            injectedFunctionExt()
        }
        isInjected = false
    }

    val colors
        get() = listOf(bold, colorT, colorB)

    init {
        if(!isRunningInTerminal()) exitProcess(0)
        Cursor.resetPos()
        Cursor.setPos(1, headerSize)
    }

    fun updateIfNeeded(updateFunc : () -> Unit = {}) {
        if (tempSize != Cursor.bounds) {
            clearAll()
            tempSize = Cursor.bounds
            updateFunc()
            injectFunction()
        }
    }

    private fun doPrtStyle(msg: Any?, codes: List<String> = emptyList()) {
        val newMsg = msg.toString().chunkedSequence(Cursor.bounds.second - Cursor.getPos().x + 1)
            .map { it.stylize(codes.ifEmpty { colors }) }
            .toList()

        for (c in newMsg) {
            if (useFooter && Cursor.getPos().y == Cursor.bounds.first - footerSize + 1) {
                Cursor.setPos(newY = footerSize + 1) // Reset cursor position if at bottom
            }
            if (useHeader && Cursor.getPos().y == headerSize) {
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

    fun println(msg: Any? = "", codes: List<String> = emptyList()) {
        doPrtStyle(msg.toString() + '\n', codes)
        updateIfNeeded()
    }

    fun print(msg: Any?, codes: List<String> = emptyList()) {
        doPrtStyle(msg, codes)
        updateIfNeeded()
    }

    private fun simplePrint(msg: Any?) {
        kotlin.io.print(msg.toString())
        if (buffered) {}
    }

    fun clear() {
        simplePrint("${ESC}3J") // Clear screen and reset cursor position
        updateIfNeeded()
    }

    fun clearAll() {
        Cursor.resetPos()
        doPrtStyle("")
        simplePrint("${ESC}J") // Clear entire screen
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

    fun writeFooter(msg: String, codes: List<String> = emptyList(), x: Int = 1, y: Int = Cursor.bounds.first) {
        if (!useFooter) return
        buffered = false
        Cursor.saveToBuffer()
        Cursor.setPos(x, y)
        simplePrint(msg.stylize(codes.ifEmpty { colors }))
        Cursor.restoreFromBuffer()
        buffered = true
        injectFunction()
    }

    fun writeHeader(msg: String, codes: List<String> = emptyList()) {
        if (!useHeader) return
        buffered = false
        Cursor.saveToBuffer()
        Cursor.resetPos()
        simplePrint(msg.stylize(codes.ifEmpty { colors }))
        Cursor.restoreFromBuffer()
        buffered = true
        injectFunction()
    }

    fun readKey(wait: Boolean = false): KeyCode =
        KeyCode(RawConsoleInput.read(wait))
}