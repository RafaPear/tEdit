package pt.rafap.tui.datatype

import pt.rafap.tui.datastore.Cursor

class Bounds(
    t: Int,
    b: Int,
    l: Int,
    r: Int,
    val hMax: Int = Cursor.bounds.first + 1,
    val vMax: Int = Cursor.bounds.second + 1
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