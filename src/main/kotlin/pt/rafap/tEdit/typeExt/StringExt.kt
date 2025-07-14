package pt.rafap.tEdit.typeExt

import pt.rafap.tEdit.tools.ESC

fun String.makeESCCode() = "$ESC$this"

fun String.center(width: Int, char: Char = ' '): String {
    return this
        .padStart((width + this.length) / 2, char)
        .padEnd(width, char)
}