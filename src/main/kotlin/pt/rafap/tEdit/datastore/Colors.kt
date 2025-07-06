package pt.isel.datastore

import pt.isel.tools.ESC
import pt.isel.tools.GetConfig
import pt.isel.tools.RESET
import java.io.File

object Colors {
    operator fun get(name: String): String {
        return config[name]?.toString() ?: ""
    }
    val config = GetConfig(File("config/codes.properties"))

    fun make(list: List<String>): String {
        val sb = StringBuilder()
        for (i in list){
            val code = config[i]
            if (code != null) {
                sb.append("${ESC}[${code}m")
            }
        }
        return sb.toString()
    }

    fun String.stylize(list: List<String>): String {
        return make(list) + this + RESET
    }
}