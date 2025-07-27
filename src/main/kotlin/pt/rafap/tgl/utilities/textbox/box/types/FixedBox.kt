package pt.rafap.tgl.utilities.textbox.box.types

import pt.rafap.tgl.utilities.textbox.box.Box
import pt.rafap.tgl.utilities.textbox.box.BoxStyle
import pt.rafap.tgl.utilities.textbox.box.textbuffer.TextBuffer
import pt.rafap.tgl.utilities.textbox.box.textbuffer.makeCenteredRectangleBounds

class FixedBox(
    override val title: String,
    override val width: Int,
    override val height: Int,
    override val style: BoxStyle
): Box {

    override val tb: TextBuffer = TextBuffer(
        makeCenteredRectangleBounds(width, height),
        style.fillChar,
        style.codes
    )
}