package pt.rafap.tgl.utilities.textbox.box.textbuffer

import pt.rafap.tgl.tui.cursor.Cursor

class Bounds(
    t: Int,
    b: Int,
    l: Int,
    r: Int,
    val hMax: Int = Cursor.bounds.first,
    val vMax: Int = Cursor.bounds.second
) {
    val top: Int = parseHBound(t)
    val bottom: Int = parseHBound(b)
    val left: Int = parseVBound(l)
    val right: Int = parseVBound(r)

    private fun parseHBound(value: Int): Int =
        value.coerceIn(1, hMax)

    private fun parseVBound(value: Int): Int =
        value.coerceIn(1, vMax)
}