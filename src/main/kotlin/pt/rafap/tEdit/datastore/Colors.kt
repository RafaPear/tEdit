package pt.rafap.tEdit.datastore

import pt.rafap.tEdit.tools.ESC
import pt.rafap.tEdit.tools.GetConfig
import pt.rafap.tEdit.tools.RESET
import java.io.File

object Colors {
    operator fun get(name: String): String {
        return config[name]?.toString() ?: ""
    }

    val config = GetConfig(File("config/colors.properties"))

    fun make(list: List<String>): String {
        val l = mutableListOf<String>()
        for (i in list) {
            if (i.isEmpty()) continue
            l += "$ESC${i}"
        }
        return l.joinToString("")
    }

    fun String.stylize(list: List<String>): String {
        val prefix = make(list)
        val suffix = RESET
        return prefix + this + suffix
    }

    fun String.stylize(name: String): String {
        return this.stylize(listOf(name))
    }
}