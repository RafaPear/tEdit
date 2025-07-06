package pt.rafap.tEdit.datastore

import com.sun.tools.javac.code.TypeAnnotationPosition.field
import pt.rafap.tEdit.tools.ESC
import pt.rafap.tEdit.tools.GetConfig
import java.io.File

object Cursor {
    private data class CursorPosition(var x: Int = 0, var y: Int = 0){
        override fun toString(): String {
            return "CursorPosition(x=$x, y=$y)"
        }
    }
    private var cPos  = CursorPosition(0, 0)
    private var tcPos = cPos

    private var x: Int
        get() = cPos.x
        set(value) { cPos.x = value }

    private var y: Int
        get() = cPos.y
        set(value) { cPos.y = value }

    var isVisible: Boolean = true
    var style = "0 q"
        set(value) {
            field = value
            print("${ESC}${field}") // Set cursor style
        }


    init {
        try {
            val configFile = File("config/cursor.properties")
            val properties = GetConfig(configFile)
            style = properties["style"].toString()
        }
        catch (e: Exception) {
            println("Error loading cursor configuration: ${e.message}")
        }
        print("${ESC}${style}")
    }

    fun resetPos() {
        x = 0
        y = 0
        print("${ESC}H")
    }

    fun setPos(newX: Int, newY: Int) {
        x = newX
        y = newY
        print("${ESC}${newY};${newX}H")
    }

    fun getPos(): Pair<Int, Int> {
        return Pair(x, y)
    }

    fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
        setPos(x, y)
    }

    fun moveUp(lines: Int) {
        y -= lines
        if (y < 0) y = 0
        print("${ESC}${y}A") // Move cursor up
    }

    fun moveDown(lines: Int) {
        y += lines
        print("${ESC}${y}B") // Move cursor up
    }

    fun moveRight(cols: Int) {
        x -= cols
        if (x < 0) x = 0
        print("${ESC}${x}C") // Move cursor up
    }

    fun moveLeft(cols: Int) {
        x += cols
        print("${ESC}${x}D") // Move cursor up
    }

    fun goToNextLineStart(lines: Int = 1) {
        y += lines
        x = 0
        print("${ESC}${y}E") // Move cursor to the start of the next line
    }

    fun goToPrevLineStart(lines: Int = 1) {
        y -= lines
        x = 0
        print("${ESC}${y}E") // Move cursor to the start of the next line
    }

    fun moveToColumn(col: Int) {
        if (col < 0) return
        x = col
        print("${ESC}${x}G") // Move cursor to the specified column in the current line
    }

    fun requestCursorPosition(): Pair<Int, Int> {
        print("${ESC}6n") // Request cursor position
        val response = StringBuilder()
        while (true) {
            val char = System.`in`.read()
            if (char == -1 || char == 0x1B) break // End of input or escape character
            response.append(char.toChar())
        }
        val position = response.toString().substringAfter('[').split(';')
        return if (position.size == 2) {
            Pair(position[0].toIntOrNull() ?: 0, position[1].toIntOrNull() ?: 0)
        } else {
            Pair(0, 0) // Default to (0, 0) if parsing fails
        }
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
        print("${ESC}${cPos.y};${cPos.x}H") // Restore cursor position
    }

    fun show() {
        if (!isVisible) {
            print("${ESC}?25h") // Show cursor
            isVisible = true
        }
    }

    fun hide() {
        if (isVisible) {
            print("${ESC}?25l") // Hide cursor
            isVisible = false
        }
    }
}