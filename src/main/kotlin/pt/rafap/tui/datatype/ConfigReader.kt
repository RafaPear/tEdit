package pt.rafap.tui.datatype

import pt.rafap.tui.logger.Logger
import pt.rafap.tui.logger.Severity
import java.io.File
import java.util.*

class ConfigReader(val file: File) : Properties() {
    operator fun get(key: String): String {
        return super[key].toString()
    }

    init {
        load()
    }

    fun load() {
        try {
            super.load(file.reader())
        }
        catch (e: Exception) {
            Logger.log("Error loading configuration file: ${e.message}", Severity.ERROR )
        }
    }
}