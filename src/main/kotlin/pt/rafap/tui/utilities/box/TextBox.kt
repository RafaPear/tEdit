package pt.rafap.tui.utilities.box

import pt.rafap.tui.datatype.TextBuffer

interface TextBox {
    val title: String

    var tb: TextBuffer

    var box: Box

    fun update()

    fun initialize() {
        box.display()
    }

    fun refresh() {
        update()
        initialize()
    }

    fun clear() {
        tb.clear()
        box = Box(title, tb)
    }
}