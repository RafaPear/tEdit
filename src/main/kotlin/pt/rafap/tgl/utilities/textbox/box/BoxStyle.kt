package pt.rafap.tgl.utilities.textbox.box

import pt.rafap.tgl.tui.color.Color
import pt.rafap.tgl.tui.color.ColorCode

class BoxStyle(
    val codes: List<ColorCode> = listOf(Color.WHITE, Color.BG_BLACK),
    val drawLines: Boolean = true,
    val tLChar: Char = '┌',
    val tRChar: Char = '┐',
    val bLChar: Char = '└',
    val bRChar: Char = '┘',
    val hBChar: Char = '─',
    val vBChar: Char = '│',
    val fillChar: Char = ' '
)