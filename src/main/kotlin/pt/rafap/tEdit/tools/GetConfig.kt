package pt.rafap.tEdit.tools

import java.io.File
import java.util.*

class GetConfig(val file: File) {
    operator fun get(key: String): Any? {
        return properties[key]
    }

    private var properties: Map<String, Any> = mutableMapOf()

    init {
        load()
    }

    private fun load() {
        try {
            val temp = Properties()
            temp.load(file.reader())
            properties = temp.entries.associate { it.key.toString() to it.value }
        }
        catch (e: Exception) {
            println("Error loading configuration file: ${e.message}")
        }
    }
}