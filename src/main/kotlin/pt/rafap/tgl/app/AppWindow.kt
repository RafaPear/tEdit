package pt.rafap.tgl.app

import pt.rafap.tgl.utilities.textbox.box.BoxStyle
import pt.rafap.tgl.utilities.textbox.box.BoxType
import pt.rafap.tgl.utilities.textbox.box.textbuffer.TextBuffer
import pt.rafap.tgl.utilities.textbox.box.textbuffer.makeRectangleBounds

class AppWindow(
    val boxStyle: BoxStyle = BoxStyle(drawLines = false),
    val boxType: BoxType = BoxType.DYNAMIC_BOX
) {

    var tb = TextBuffer(makeRectangleBounds(1, 1))

    init {
        tb
    }

    fun display() {
        tb.printUpdate()
    }

    fun clear() {
        tb.clear()
    }

    fun refresh() {

    }

    fun rePrint() {
        tb.printUpdate(true)
    }

    fun update() {
        val tbOLD = tb.getBuffer()
        tb = TextBuffer(makeRectangleBounds(1, 1))
        tb.setBuffer(tbOLD)
    }
}