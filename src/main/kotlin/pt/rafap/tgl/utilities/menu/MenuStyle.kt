package pt.rafap.tgl.utilities.menu

import pt.rafap.tgl.tui.color.Color
import pt.rafap.tgl.tui.color.ColorCode

class MenuStyle(
    val defaultCodes: List<ColorCode> = listOf(Color.BLUE, Color.BG_WHITE),
    val highlightCodes: List<ColorCode> = listOf(Color.WHITE, Color.BG_BLUE),
    val disabledCodes: List<ColorCode> = listOf(Color.BLACK, Color.BG_WHITE),
    val effectCodes: List<ColorCode> = emptyList()
){
    operator fun get(node: MenuNode): List<ColorCode>{
        return if(!node.availableFun()) {
            disabledCodes + effectCodes
        } else if (node.isSelected) {
            highlightCodes + effectCodes
        } else {
            defaultCodes + effectCodes
        }
    }
}