package pt.rafap.tgl.tui.cursor

data class CursorPosition(var x: Int = 1, var y: Int = 1){
    override fun toString(): String {
        return "CursorPosition(x=$x, y=$y)"
    }
}