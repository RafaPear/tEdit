package pt.isel.logger

import pt.isel.datastore.Colors
import pt.isel.tools.ESC

enum class Severity(val level: Int, val color: String, val title: String) {
    INFO(1, "$ESC${Colors["GREEN"]}", "INFO: "), // Green
    WARNING(2, "$ESC${Colors["YELLOW"]}", "WARNING: "), // Yellow
    ERROR(3, "$ESC${Colors["RED"]}", "ERROR: "), // Red
    CRITICAL(4, "$ESC${Colors["MAGENTA"]}", "CRITICAL: "), // Magenta
    DEBUG(5,"$ESC${Colors["BLUE"]}","DEBUG: " ), // Blue
}