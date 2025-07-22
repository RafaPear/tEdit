package pt.rafap.tui.logger

import pt.rafap.tui.datastore.Color
import pt.rafap.tui.datatype.ColorCode

enum class Severity(val level: Int, val color: ColorCode, val title: String) {
    DEBUG(0, Color.BLUE, "DEBUG: "),
    INFO(1, Color.GREEN, "INFO: "),
    WARNING(2, Color.YELLOW, "WARNING: "),
    ERROR(3, Color.RED, "ERROR: "),
    CRITICAL(4, Color.MAGENTA, "CRITICAL: ");
}