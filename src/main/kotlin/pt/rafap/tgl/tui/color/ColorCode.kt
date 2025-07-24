package pt.rafap.tgl.tui.color

import pt.rafap.tgl.tui.tools.ESC

class ColorCode(code: String) {
    val code = "${ESC}$code"
}