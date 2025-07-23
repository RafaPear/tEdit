package pt.rafap.tui.utilities.box

import pt.rafap.tui.datastore.Cursor
import pt.rafap.tui.datatype.Bounds

fun makeSquareBounds(size: Int): Bounds{
    val screenCenter = Cursor.bounds.first / 2 to Cursor.bounds.second / 2
    val halfSize = size / 2
    return Bounds(
        screenCenter.first - halfSize/2,
        screenCenter.first + halfSize/2,
        screenCenter.second - halfSize,
        screenCenter.second + halfSize
    )
}

fun makeRectangleBounds(
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