package pt.isel.logger

import pt.isel.datastore.Colors
import pt.isel.tools.ESC

object Logger {
    private val log = StringBuilder()

    var severity = Severity.DEBUG

    fun log(message: String, sev: Severity = Severity.INFO) {
        if (sev.level < this.severity.level) return
        println("$ESC${sev.color}$message${ESC}${Colors["RESET"]}")
        log.append(message).append("\n")
    }

    fun getLog(): String {
        return log.toString()
    }

    fun clear() {
        log.clear()
    }
}