package pt.rafap.tui.logger

import pt.rafap.tui.TUI
import pt.rafap.tui.datastore.Cursor
import pt.rafap.tui.datatype.ColorCode
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {
    private val log = StringBuilder()

    var severity = Severity.DEBUG

    var printFun: (String, List<ColorCode>) -> Unit = { message, codes ->
        Cursor.runWithoutChange {
            TUI.writeFooter(message,codes)
        }
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