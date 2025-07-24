package pt.rafap.tgl.utilities.textbox.box

import pt.rafap.tgl.utilities.textbox.box.textbuffer.TextBuffer
import pt.rafap.tgl.utilities.textbox.box.textbuffer.makeRectangleBounds

class DynamicBox(
    override val title: String,
    override val width: Int,
    override val height: Int,
    override val style: BoxStyle
): Box {

    override val tb: TextBuffer = TextBuffer(
        makeRectangleBounds(width, height),
        style.fillChar,
        style.codes
    )
}