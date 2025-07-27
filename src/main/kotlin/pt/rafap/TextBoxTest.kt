package pt.rafap

import pt.rafap.tgl.TGL
import pt.rafap.tgl.apps.sys.TextEditor
import pt.rafap.tgl.tui.tools.openTerminalIfNotRunning

fun main() {
    openTerminalIfNotRunning()

    val app = TextEditor()

    /*val boxStyle = BoxStyle(
        listOf(Color.WHITE, Color.BG_BLACK),
        true
    )

    val tb = TextRectangle(
        "",
        1, 1,
        BoxType.DYNAMIC_BOX,
        boxStyle
    )

    tb.display()*/

    TGL.init()

    TGL.appRun(app)

    TGL.exit()
}