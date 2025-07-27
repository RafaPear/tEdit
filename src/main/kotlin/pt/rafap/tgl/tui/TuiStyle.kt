package pt.rafap.tgl.tui

import pt.rafap.tgl.tui.color.Color
import pt.rafap.tgl.tui.color.ColorCode

class TuiStyle(
    val defaultCodes: List<ColorCode> = listOf(Color.BLUE, Color.BG_WHITE),
    val effectCodes: List<ColorCode> = emptyList()
) {
    // returns default + effect codes
    operator fun invoke(): List<ColorCode> {
        return defaultCodes + effectCodes
    }
}