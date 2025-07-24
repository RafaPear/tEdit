package pt.rafap.tgl.utilities.textbox.box

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
            DYNAMIC_BOX -> DynamicBox(title, width, height, style).apply { update() }
            FIXED_BOX -> FixedBox(title, width, height, style).apply { update() }
        }
    }
}