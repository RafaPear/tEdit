package pt.rafap.tgl.utilities.textbox.box.textbuffer

import pt.rafap.tgl.tui.TUI
import pt.rafap.tgl.tui.cursor.Cursor

class Bounds(
    t: Int,
    b: Int,
    l: Int,
    r: Int,
    vMax: Int = Cursor.bounds.first,
    vMin: Int = Cursor.minBounds.first + 1,
    hMax: Int = Cursor.bounds.second,
    hMin: Int = Cursor.minBounds.second + 1
) {
    private class Limits(val value: Int, val min: Int, val max: Int)

    private val tLim: Limits = Limits(t, TUI.headerSize + 2, vMax)
    private val bLim: Limits = Limits(b, TUI.footerSize + 1, vMax)
    private val lLim: Limits = Limits(l, hMin, hMax)
    private val rLim: Limits = Limits(r, hMin, hMax)

    val top: Int = tLim.capValue()
    val bottom: Int = bLim.capValue()
    val left: Int = lLim.capValue()
    val right: Int = rLim.capValue()

    private fun Limits.capValue(): Int =
        value.coerceIn(min, max)
}