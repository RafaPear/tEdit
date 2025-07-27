package pt.rafap.tgl.utilities.textbox

import pt.rafap.tgl.utilities.textbox.box.Box
import pt.rafap.tgl.utilities.textbox.box.BoxStyle
import pt.rafap.tgl.utilities.textbox.box.BoxType

interface TextBox {
    val title: String
    val width: Int
    val height: Int
    val type: BoxType
    val style: BoxStyle
        get() = BoxStyle()

    var box: Box

    fun update() {
        box = type.handle(title, width, height, style)
        box.initialize()
    }

    fun clear() {
        box.tb.clear()
    }

    fun display() {
        box.display()
    }

    fun rePrint() {
        box.rePrint()
    }

    fun refresh() {
        clear()
        update()
    }
}