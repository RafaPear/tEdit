package pt.rafap.tgl.tui.typeExt

fun String.center(width: Int, char: Char = ' '): String {
    return this
        .padStart((width + this.length) / 2, char)
        .padEnd(width, char)
}