package pt.rafap.tgl.tui

import pt.rafap.tgl.tui.color.Color
import pt.rafap.tgl.tui.color.Color.stylize
import pt.rafap.tgl.tui.color.ColorCode
import pt.rafap.tgl.tui.cursor.Cursor
import pt.rafap.tgl.tui.keyboard.KeyCode
import pt.rafap.tgl.tui.logger.Logger
import pt.rafap.tgl.tui.logger.Severity
import pt.rafap.tgl.tui.tools.ConfigReader
import pt.rafap.tgl.tui.tools.ESC
import pt.rafap.tgl.tui.tools.isRunningInTerminal
import pt.rafap.tgl.tui.keyboard.RawConsoleInput
import java.io.File
import kotlin.system.exitProcess

object TUI {
    val config = ConfigReader(File("config/tui.properties"))

    var colorT = Color.WHITE
    var colorB = Color.BG_BLACK

    var tempSize = Cursor.bounds

    private var useBold: Boolean = config["useBold"].toBoolean()
    private val bold = if (useBold) Color.BOLD else Color.RESET_BOLD

    var useFooter: Boolean = config["useFooter"].toBoolean()

    var useHeader: Boolean = config["useHeader"].toBoolean()

    var footerSize: Int =
        try {
            config["footerSize"].toInt()
        } catch (e: Exception) {
            1
        }

    var headerSize: Int =
        try {
            config["headerSize"].toInt()
        } catch (e: Exception) {
            1
        }

    private var isInjected: Boolean = false

    var injectedFunctionExt: (() -> Unit) = {
        val prevFun = Logger.printFun
        Logger.printFun = { message, codes ->
            writeFooter(message, codes)
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
            Thread.sleep(1)
            clearAll()
            tempSize = Cursor.bounds
            updateFunc()
            injectFunction()
        }
    }

    private fun doPrtStyle(msg: Any?, codes: List<ColorCode> = emptyList()) {
        val newMsg = msg.toString().chunkedSequence(Cursor.bounds.second - Cursor.getPos().x + 1)
            .map { it.stylize(codes) }
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

    fun println(msg: Any? = "", codes: List<ColorCode> = emptyList()) {
        doPrtStyle(msg.toString() + '\n', codes)
        updateIfNeeded()
    }

    fun print(msg: Any?, codes: List<ColorCode> = emptyList()) {
        doPrtStyle(msg, codes)
        updateIfNeeded()
    }

    private fun simplePrint(msg: Any?) {
        kotlin.io.print(msg.toString())
    }

    fun clear() {
        simplePrint("${ESC}3J") // Clear screen and reset cursor position
        updateIfNeeded()
    }

    fun clearAll() {
        simplePrint("${ESC}0J") // Clear entire screen
        simplePrint("${ESC}1J") // Clear entire screen
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

    fun writeFooter(msg: String, codes: List<ColorCode> = emptyList(), x: Int = 1, y: Int = Cursor.bounds.first) {
        if (!useFooter) return
        Cursor.setPos(x, y)
        simplePrint(msg.stylize(codes.ifEmpty { colors }))
        clearLineToEnd()
        injectFunction()
    }

    fun writeHeader(msg: String, codes: List<ColorCode> = emptyList()) {
        if (!useHeader) return
        Cursor.resetPos()
        simplePrint(msg.stylize(codes.ifEmpty { colors }))
        clearLineToEnd()
        injectFunction()
    }

    fun readKey(wait: Boolean = false): KeyCode =
        KeyCode(RawConsoleInput.read(wait))
}