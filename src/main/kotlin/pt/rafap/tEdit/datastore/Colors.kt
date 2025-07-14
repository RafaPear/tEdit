package pt.rafap.tEdit.datastore

import pt.rafap.tEdit.datatype.ConfigReader
import pt.rafap.tEdit.logger.Logger
import pt.rafap.tEdit.logger.Severity
import pt.rafap.tEdit.tools.ESC
import java.io.File

object Colors {
    operator fun get(name: String): String {
        return config[name]?.toString() ?: ""
    }

    val config = ConfigReader(File("config/colors.properties"))

    const val FALLBACK_COLOR: String = "WHITE"

    const val WHITE             : String = "WHITE"
    const val BLACK             : String = "BLACK"
    const val RED               : String = "RED"
    const val GREEN             : String = "GREEN"
    const val YELLOW            : String = "YELLOW"
    const val BLUE              : String = "BLUE"
    const val MAGENTA           : String = "MAGENTA"
    const val CYAN              : String = "CYAN"

    const val BG_WHITE          : String = "BG_WHITE"
    const val BG_BLACK          : String = "BG_BLACK"
    const val BG_RED            : String = "BG_RED"
    const val BG_GREEN          : String = "BG_GREEN"
    const val BG_YELLOW         : String = "BG_YELLOW"
    const val BG_BLUE           : String = "BG_BLUE"
    const val BG_MAGENTA        : String = "BG_MAGENTA"
    const val BG_CYAN           : String = "BG_CYAN"

    const val BOLD              : String = "BOLD"
    const val RESET_BOLD        : String = "RESET_BOLD"
    const val ITALIC            : String = "ITALIC"
    const val RESET_ITALIC      : String = "RESET_ITALIC"
    const val UNDERLINE         : String = "UNDERLINE"
    const val RESET_UNDERLINE   : String = "RESET_UNDERLINE"
    const val STRIKE            : String = "STRIKETHROUGH"
    const val RESET_STRIKE      : String = "RESET_STRIKETHROUGH"
    const val BLINKING          : String = "BLINKING"
    const val RESET_BLINKING    : String = "RESET_BLINKING"
    const val INVISIBLE         : String = "INVISIBLE"
    const val RESET_INVISIBLE   : String = "RESET_INVISIBLE"
    const val REVERSE           : String = "REVERSE"
    const val RESET_REVERSE     : String = "RESET_REVERSE"
    const val RESET_ALL         : String = "RESET_ALL"

    fun make(list: List<String>): String =
        list.joinToString("") {
            val code = config[it] as? String
            if (code == null) {
                Logger.log(
                    "Style or color '$it' not found in colors configuration. Falling Back to Default ($FALLBACK_COLOR)",
                    Severity.DEBUG
                )
                "$ESC${config[FALLBACK_COLOR]}"
            } else "$ESC$code"
        }

    fun String.stylize(color: List<String>, reset: Boolean = true): String {
        return if (reset) "$ESC${config[RESET_ALL]}" + make(color) + this + ESC + config[RESET_ALL]
        else make(color) + this

    }
}