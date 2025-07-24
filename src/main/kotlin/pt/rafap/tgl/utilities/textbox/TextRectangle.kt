package pt.rafap.tgl.utilities.textbox

import pt.rafap.tgl.utilities.textbox.box.Box
import pt.rafap.tgl.utilities.textbox.box.BoxStyle
import pt.rafap.tgl.utilities.textbox.box.BoxType

class TextRectangle(
    override val title: String = "",
    override val width: Int,
    override val height: Int,
    override val type: BoxType = BoxType.FIXED_BOX,
    override val style: BoxStyle
): TextBox {
    override var box: Box = type.handle(title, width, height, style)
}