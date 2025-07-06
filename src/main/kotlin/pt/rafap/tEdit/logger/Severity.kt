package pt.rafap.tEdit.logger

import pt.rafap.tEdit.datastore.Colors
import pt.rafap.tEdit.tools.ESC

enum class Severity(val level: Int, val color: String, val title: String) {
    DEBUG(0, Colors["BLUE"], "DEBUG: "),
    INFO(1, Colors["GREEN"], "INFO: "),
    WARNING(2, Colors["YELLOW"], "WARNING: "),
    ERROR(3, Colors["RED"], "ERROR: "),
    CRITICAL(4, Colors["MAGENTA"], "CRITICAL: ");
}