package pt.rafap.tgl.utilities.textbox.box.textbuffer

import pt.rafap.tgl.tui.cursor.Cursor

fun makeCenteredRectangleBounds(
    width: Int,
    height: Int
): Bounds {
    val screenCenter = Cursor.bounds.first / 2 + 1 to Cursor.bounds.second / 2
    return Bounds(
        screenCenter.first - height / 4,
        screenCenter.first + height / 4,
        screenCenter.second - width / 2,
        screenCenter.second + width / 2
    )
}

fun makeRectangleBounds(
    hMargin: Int,
    vMargin: Int
): Bounds {
    val screenCenter = Cursor.bounds.first / 2 + 1 to Cursor.bounds.second / 2
    val nH = hMargin.cap(screenCenter.second - 2)
    val nV = vMargin.cap(screenCenter.first - 2)
    return Bounds(
        nV,
        Cursor.bounds.first - nV,
        nH,
        Cursor.bounds.second - nH
    )
}

private fun Int.cap(max: Int): Int {
    return this.coerceIn(1, max)
}