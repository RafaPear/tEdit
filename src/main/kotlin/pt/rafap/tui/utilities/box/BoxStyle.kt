package pt.rafap.tui.utilities.box

import pt.rafap.tui.datastore.Color
import pt.rafap.tui.datatype.ColorCode

class BoxStyle(
    val codes: List<ColorCode> = listOf(Color.WHITE, Color.BG_BLACK),
    val tLChar: Char = '┌',
    val tRChar: Char = '┐',
    val bLChar: Char = '└',
    val bRChar: Char = '┘',
    val hBChar: Char = '─',
    val vBChar: Char = '│',
    val fillChar: Char = ' '
)