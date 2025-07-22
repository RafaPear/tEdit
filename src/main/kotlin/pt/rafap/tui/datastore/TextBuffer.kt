package pt.rafap.tui.datastore

import pt.rafap.tui.TUI

// Text Buffer using 2d array
class TextBuffer {
    val size = calculateBufferSize()
    private val buffer = MutableList(size) { "" }
    private val cPos
        get() = Cursor.getPos()

    private fun calculateBufferSize(): Int {
        var final = Cursor.bounds.first
        if (TUI.useHeader) final -= TUI.headerSize
        if (TUI.useFooter) final -= TUI.footerSize
        return final
    }

    fun getText(): String {
        return buffer.joinToString("\n")
    }

}