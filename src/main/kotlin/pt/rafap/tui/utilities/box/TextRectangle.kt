package pt.rafap.tui.utilities.box

import pt.rafap.tui.datastore.Color
import pt.rafap.tui.datatype.TextBuffer

class TextRectangle(
    val title: String = "",
    val width: Int,
    val height: Int,
    style: BoxStyle
) {
    private var tb = TextBuffer(
        makeRectangleBounds(width, height),
        style.fillChar,
        style.codes
    )

    private var box = Box(title, tb)

    fun initialize() {
        box.display()
    }

    fun update() {
        tb = TextBuffer(
            makeRectangleBounds(width, height),
            ' ',
            listOf(Color.BLUE, Color.BG_WHITE)
        )
        box = Box(title, tb)
    }

    fun refresh() {
        update()
        initialize()
    }
}