package pt.rafap.tEdit.logger

import pt.rafap.tEdit.tui.TUI
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {
    private val log = StringBuilder()

    var severity = Severity.DEBUG

    var printFun: (String, List<String>) -> Unit = { message, codes ->
        TUI.println(message,codes)
    }

    fun log(message: String, sev: Severity = Severity.INFO) {
        if (sev.level < this.severity.level) return
        val timestamp = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
        val msg = "[${timestamp}] ${sev.title}$message"
        printFun(msg, listOf(sev.color))
        log.append(message).append("\n")
    }

    fun error(message: String) {
        log(message, Severity.ERROR)
    }

    fun getLog(): String {
        return log.toString()
    }

    fun clear() {
        log.clear()
    }

    fun writeToFile(filePath: String) {
        try {
            File(filePath).apply {
                parentFile?.mkdirs() // Ensure parent directories exist
                createNewFile() // Create the file if it doesn't exist
            }.printWriter().print(log.toString())
        } catch (e: Exception) {
            log("Error writing log to file: ${e.message}", Severity.ERROR)
        }
    }
}