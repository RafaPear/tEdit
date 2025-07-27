package pt.rafap.tgl.tui.logger

import pt.rafap.tgl.tui.TUI
import pt.rafap.tgl.tui.color.ColorCode
import pt.rafap.tgl.tui.cursor.Cursor
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {
    private val log = StringBuilder()

    var severity = Severity.DEBUG

    var print = false

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

        if (print) printFun(msg, listOf(sev.color))

        log.append(msg).append("\n")
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
            val file = File(filePath)
            file.writeText(log.toString())
        } catch (e: Exception) {
            error("Failed to write log to file: ${e.message}")
        }
    }
}