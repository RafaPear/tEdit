package pt.rafap.tgl.utilities.textbox.box

import pt.rafap.tgl.utilities.textbox.box.types.DynamicBox
import pt.rafap.tgl.utilities.textbox.box.types.FixedBox

enum class BoxType {
    DYNAMIC_BOX,
    FIXED_BOX;

    fun handle(
        title: String,
        width: Int,
        height: Int,
        style: BoxStyle
    ): Box {
        return when (this) {
            DYNAMIC_BOX -> DynamicBox(title, width, height, style).apply { initialize() }
            FIXED_BOX -> FixedBox(title, width, height, style).apply { initialize() }
        }
    }
}