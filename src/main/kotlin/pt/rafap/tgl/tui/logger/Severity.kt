package pt.rafap.tgl.tui.logger

import pt.rafap.tgl.tui.color.Color
import pt.rafap.tgl.tui.color.ColorCode

enum class Severity(val level: Int, val color: ColorCode, val title: String) {
    DEBUG(0, Color.BLUE, "DEBUG: "),
    INFO(1, Color.GREEN, "INFO: "),
    WARNING(2, Color.YELLOW, "WARNING: "),
    ERROR(3, Color.RED, "ERROR: "),
    CRITICAL(4, Color.MAGENTA, "CRITICAL: ");
}