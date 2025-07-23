package pt.rafap.tui.utilities.box

import pt.rafap.tui.datastore.Color
import pt.rafap.tui.datatype.TextBuffer

class TextBox(
    val title: String = "",
    val size: Int = 10,
) {
    private var tb = TextBuffer(
        makeSquareBounds(size),
        ' ',
        listOf(Color.BLUE, Color.BG_WHITE)
    )

    private var box = Box(title,tb)

    fun initialize() {
        box.display()
    }

    fun update() {
        tb = TextBuffer(
            makeSquareBounds(size),
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