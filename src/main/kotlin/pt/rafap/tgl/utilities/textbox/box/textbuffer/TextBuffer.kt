package pt.rafap.tgl.utilities.textbox.box.textbuffer

import pt.rafap.tgl.tui.TUI
import pt.rafap.tgl.tui.color.Color
import pt.rafap.tgl.tui.color.ColorCode
import pt.rafap.tgl.tui.cursor.Cursor
import kotlin.text.iterator

// Text Buffer using 2d array
class TextBuffer(
    bounds: Bounds,
    val defaultChar: Char = ' ',
    val defaultCodes: List<ColorCode> = listOf(Color.WHITE, Color.BG_BLACK)
) {

    val top: Int = bounds.top
    val bottom: Int = bounds.bottom
    val left: Int = bounds.left
    val right: Int = bounds.right

    val width: Int
        get() = right - left

    val height: Int
        get() = bottom - top

    // Adicionar se ja est impresso ou nao
    data class Entry(
        val char: Char,
        val codes: List<ColorCode> = emptyList(),
        var isPrinted: Boolean = false
    )

    private var x: Int = left
        set(value) {
            var temp = value
            if (temp < left) temp = left
            temp %= right
            if (temp < left) temp = left
            field = temp
        }

    private var y: Int = top
        set(value) {
            var temp = value
            if (temp < top) temp = top
            temp %= bottom
            if (temp < top) temp = top
            field = temp
        }

    private val buffer: Array<Array<Entry>> =
        Array(height) {
            Array(width) {
                Entry(defaultChar, defaultCodes)
            }
        }

    fun getBuffer(): Array<Array<Entry>> {
        return buffer
    }

    fun print(text: String) {
        Cursor.setPos(x, y)
        for (c in text) {
            setChar(c, true)
            TUI.print(c, defaultCodes)
            Cursor.setPos(x, y)
        }
    }

    fun print(ch: Char) {
        Cursor.setPos(x, y)
        setChar(ch, true)
        TUI.print(ch, defaultCodes)
    }

    fun add(text: String) {
        for (c in text) {
            setChar(c)
        }
    }

    fun printUpdate(codes: List<ColorCode> = defaultCodes) {
        var tempY = top
        for (row in buffer) {
            var tmpStr = ""
            var needsPrinting = false
            for (entry in row) {
                tmpStr += entry.char
                if (!entry.isPrinted) {
                    entry.isPrinted = true
                    needsPrinting = true
                }
            }
            if (needsPrinting){
                Cursor.setPos(left, tempY)
                TUI.print(tmpStr, codes)
            }
            tempY++
        }
        Cursor.resetPos()
    }

    fun clear() {
        for (i in 0 until height) {
            for (j in 0 until width) {
                buffer[i][j] = Entry(defaultChar, listOf(Color.BLACK, Color.BG_BLACK), false)
            }
        }
        printUpdate(listOf(Color.BLACK, Color.BG_BLACK))
        x = left
        y = top
    }

    fun setChar(char: Char, printed: Boolean = false) {
        val (nX, nY) = posToIndex(x, y)
        buffer[nY][nX] = Entry(char, defaultCodes, printed)
        incX()
    }

    fun deleteChar() {
        val (nX, nY) = posToIndex(x, y)
        decX()
        buffer[nY][nX] = Entry(defaultChar, defaultCodes, false)
    }

    fun posToIndex(x: Int, y: Int): Pair<Int, Int> {
        val nX = if (x - left < 0) 0 else x - left
        val nY = if (y - top < 0) 0 else y - top
        return Pair(nX, nY)
    }

    fun incX() {
        if (x + 1 >= right) {
            incY()
        }
        x++
    }

    fun decX() {
        if (x - 1 < left) {
            x = right - 1
            decY()
        }
        else x--
    }

    fun incY() { y++ }

    fun decY() { y-- }
}