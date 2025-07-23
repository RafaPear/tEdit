package pt.rafap.tui.utilities.box

import pt.rafap.tui.datastore.Color
import pt.rafap.tui.datatype.TextBuffer

class TextSquare(
    override val title: String = "",
    val size: Int = 10,
    style: BoxStyle,
): TextBox {
    override var tb = TextBuffer(
        makeSquareBounds(size),
        style.fillChar,
        style.codes
    )

    override var box = Box(title,tb)

    override fun update() {
        tb = TextBuffer(
            makeSquareBounds(size),
            ' ',
            listOf(Color.BLUE, Color.BG_WHITE)
        )
        box = Box(title, tb)
    }

    override fun refresh() {
        update()
        initialize()
    }
}