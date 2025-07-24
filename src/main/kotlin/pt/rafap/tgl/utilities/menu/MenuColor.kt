package pt.rafap.tgl.utilities.menu

import pt.rafap.tgl.tui.color.Color
import pt.rafap.tgl.tui.color.ColorCode

class MenuColor(
    val fgColor: ColorCode = Color.BLUE,
    val bgColor: ColorCode = Color.BG_WHITE,
    val highlightFgColor: ColorCode = Color.WHITE,
    val highlightBgColor: ColorCode = Color.BG_BLUE,
    val disabledFgColor: ColorCode = Color.BLACK,
    val disabledBgColor: ColorCode = Color.BG_WHITE,
    val effect: ColorCode = Color.NONE
){
    operator fun get(node: MenuNode): List<ColorCode>{
        return if(!node.availableFun()) {
            listOf(disabledFgColor, disabledBgColor, effect)
        } else if (node.isSelected) {
            listOf(highlightFgColor, highlightBgColor, effect)
        } else {
            listOf(fgColor, bgColor, effect)
        }
    }
}