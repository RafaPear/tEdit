package pt.rafap.tEdit.logger

enum class Severity(val level: Int, val color: String, val title: String) {
    DEBUG(0, "BLUE", "DEBUG: "),
    INFO(1, "GREEN", "INFO: "),
    WARNING(2, "YELLOW", "WARNING: "),
    ERROR(3, "RED", "ERROR: "),
    CRITICAL(4, "MAGENTA", "CRITICAL: ");
}