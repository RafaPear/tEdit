package pt.rafap.tui.datatype

import pt.rafap.tui.tools.ESC

class ColorCode(code: String) {
    val code = "$ESC$code"
}