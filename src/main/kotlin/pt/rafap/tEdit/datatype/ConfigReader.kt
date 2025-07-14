package pt.rafap.tEdit.datatype

import pt.rafap.tEdit.logger.Logger
import pt.rafap.tEdit.logger.Severity
import java.io.File
import java.util.*

class ConfigReader(val file: File) : Properties() {
    operator fun get(key: String): Any? {
        return super[key]
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