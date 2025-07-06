package pt.rafap.tEdit.logger

import pt.rafap.tEdit.datastore.Colors
import pt.rafap.tEdit.datastore.Colors.stylize
import pt.rafap.tEdit.tools.ESC

object Logger {
    private val log = StringBuilder()

    var severity = Severity.DEBUG

    fun log(message: String, sev: Severity = Severity.INFO) {
        if (sev.level < this.severity.level) return
        println("${sev.title}$message".stylize(sev.color))
        log.append(message).append("\n")
    }

    fun getLog(): String {
        return log.toString()
    }

    fun clear() {
        log.clear()
    }
}