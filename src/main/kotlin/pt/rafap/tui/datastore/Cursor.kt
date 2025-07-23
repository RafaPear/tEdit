package pt.rafap.tui.datastore

import pt.rafap.tui.datatype.ConfigReader
import pt.rafap.tui.datatype.CursorPosition
import pt.rafap.tui.tools.ESC
import pt.rafap.tui.tools.getTerminalSize
import java.io.File

object Cursor {
    private var cPos  = CursorPosition()
    private var tcPos = cPos

    private var x: Int
        get() = cPos.x
        set(value) {
            var temp = value
            if (temp == -1) temp = bounds.second
            if (temp <= 1) temp = 1
            temp %= (bounds.second + 1)
            cPos.x = temp
        }

    private var y: Int
        get() = cPos.y
        set(value) {
            var temp = value
            if (temp == -1) temp = bounds.first
            if (temp <= 1) temp = 1
            temp %= (bounds.first + 1)
            cPos.y = temp
        }

    var isVisible: Boolean = false
        set(value) {
            field = value
            if (value) {
                print("${ESC}?25h") // Show cursor
            } else {
                print("${ESC}?25l") // Hide cursor
            }
        }
    var style = "0 q"
        set(value) {
            field = value
            print("${ESC}${field}") // Set cursor style
        }

    val bounds: Pair<Int, Int>
        get() = requestSize()

    private var ptrBounds: Pair<Int, Int> = getTerminalSize()

    private fun requestSize(): Pair<Int, Int> {
        var tempSize = ptrBounds
        if (lastSizeRequest + 50 < System.currentTimeMillis()) {
            lastSizeRequest = System.currentTimeMillis()
            tempSize = getTerminalSize()
        }

        ptrBounds = tempSize
        return tempSize
    }

    private var lastSizeRequest = 0L

    init {
        try {
            val configFile = File("config/cursor.properties")
            val properties = ConfigReader(configFile)
            style = properties["style"]
        }
        catch (e: Exception) {
            println("Error loading cursor configuration: ${e.message}")
        }
        print("${ESC}${style}")
        print("${ESC}=7h")
        isVisible = false
        bounds
    }

    fun resetPos() {
        x = 1
        y = 1
        print("${ESC}H")
    }

    fun setPos(newX: Int = x, newY: Int = y) {
        x = newX
        y = newY
        print("${ESC}${y};${x}H")
    }

    fun addToX(dx: Int) {
        x += dx
    }

    fun addToY(dy: Int) {
        y += dy
    }

    fun addToPos(dx: Int, dy: Int) {
        addToX(dx)
        addToY(dy)
        updateSavedPos()
    }

    private fun calculateNewY(msg: Any?): Int {
        val size = msg.toString().replace(Regex("\u001B\\[[\\d;]*[^\\d;]"),"").length
        val width = bounds.second
        return if (x + size > width) {
            size / width + if (size % width == 0) 0 else 1
        } else 0
    }

    fun doPrt(msg: Any?){
        val dy = calculateNewY(msg) + msg.toString().count { it == '\n' }
        val size = msg.toString().replace(Regex("\u001B\\[[\\d;]*[^\\d;]"),"").length
        if (msg.toString().contains('\n'))
            goToNextLineStart(dy)
        else
            addToPos(size, dy)
    }

    fun setPos(newPos: CursorPosition) {
        x = newPos.x
        y = newPos.y
        print("${ESC}${y};${x}H")
    }

    fun getPos(): CursorPosition {
        return CursorPosition(x, y)
    }

    fun updatePos(newX: Int, newY: Int) {
        x = newX
        y = newY
    }

    fun updateSavedPos() {
        setPos(CursorPosition(x, y))
    }

    fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
        setPos(x, y)
    }

    fun moveUp(lines: Int = 1) {
        y -= lines
        print("${ESC}${y}A") // Move cursor up
    }

    fun moveDown(lines: Int = 1) {
        y += lines
        print("${ESC}${lines}B") // Move cursor down
    }

    fun moveRight(cols: Int = 1) {
        x += cols
        print("${ESC}${cols}C") // Move cursor right
    }

    fun moveLeft(cols: Int = 1) {
        x -= cols
        print("${ESC}${cols}D") // Move cursor left
    }

    fun goToNextLineStart(lines: Int = 1) {
        y += lines
        x = 1
        updateSavedPos()
    }

    fun goToPrevLineStart(lines: Int = 1) {
        y -= lines
        x = 1
        updateSavedPos()
    }

    fun moveToColumn(col: Int) {
        if (col <= 0) return
        x = col
        print("${ESC}${x}G") // Move cursor to the specified column in the current line
    }

    fun moveLineUp(){
        y++
        print("${ESC}M") // Move cursor up to the start of the line
    }

    fun saveToBuffer() {
        tcPos = CursorPosition(cPos.x, cPos.y)
    }

    fun restoreFromBuffer() {
        cPos = tcPos
        updateSavedPos()
    }

    fun runWithoutChange(func: () -> Unit){
        saveToBuffer()
        func()
        restoreFromBuffer()
    }
}