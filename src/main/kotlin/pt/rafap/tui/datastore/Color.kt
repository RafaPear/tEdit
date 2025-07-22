package pt.rafap.tui.datastore

import pt.rafap.tui.datatype.ColorCode
import pt.rafap.tui.datatype.ConfigReader
import java.io.File

object Color {
    operator fun get(name: String): String {
        return config[name]
    }

    val config = ConfigReader(File("config/colors.properties"))

    val WHITE             : ColorCode = ColorCode(config["WHITE"])
    val BLACK             : ColorCode = ColorCode(config["BLACK"])
    val RED               : ColorCode = ColorCode(config["RED"])
    val GREEN             : ColorCode = ColorCode(config["GREEN"])
    val YELLOW            : ColorCode = ColorCode(config["YELLOW"])
    val BLUE              : ColorCode = ColorCode(config["BLUE"])
    val MAGENTA           : ColorCode = ColorCode(config["MAGENTA"])
    val CYAN              : ColorCode = ColorCode(config["CYAN"])

    val BG_WHITE          : ColorCode = ColorCode(config["BG_WHITE"])
    val BG_BLACK          : ColorCode = ColorCode(config["BG_BLACK"])
    val BG_RED            : ColorCode = ColorCode(config["BG_RED"])
    val BG_GREEN          : ColorCode = ColorCode(config["BG_GREEN"])
    val BG_YELLOW         : ColorCode = ColorCode(config["BG_YELLOW"])
    val BG_BLUE           : ColorCode = ColorCode(config["BG_BLUE"])
    val BG_MAGENTA        : ColorCode = ColorCode(config["BG_MAGENTA"])
    val BG_CYAN           : ColorCode = ColorCode(config["BG_CYAN"])

    val BOLD              : ColorCode = ColorCode(config["BOLD"])
    val RESET_BOLD        : ColorCode = ColorCode(config["RESET_BOLD"])
    val ITALIC            : ColorCode = ColorCode(config["ITALIC"])
    val RESET_ITALIC      : ColorCode = ColorCode(config["RESET_ITALIC"])
    val UNDERLINE         : ColorCode = ColorCode(config["UNDERLINE"])
    val RESET_UNDERLINE   : ColorCode = ColorCode(config["RESET_UNDERLINE"])
    val STRIKE            : ColorCode = ColorCode(config["STRIKETHROUGH"])
    val RESET_STRIKE      : ColorCode = ColorCode(config["RESET_STRIKETHROUGH"])
    val BLINKING          : ColorCode = ColorCode(config["BLINKING"])
    val RESET_BLINKING    : ColorCode = ColorCode(config["RESET_BLINKING"])
    val INVISIBLE         : ColorCode = ColorCode(config["INVISIBLE"])
    val RESET_INVISIBLE   : ColorCode = ColorCode(config["RESET_INVISIBLE"])
    val REVERSE           : ColorCode = ColorCode(config["REVERSE"])
    val RESET_REVERSE     : ColorCode = ColorCode(config["RESET_REVERSE"])
    val RESET_ALL         : ColorCode = ColorCode(config["RESET_ALL"])

    val NONE              : ColorCode = ColorCode(config["BOLD"] + RESET_BOLD.code) // No color, no style

    fun make(list: List<ColorCode>): String = list.joinToString("") { it.code }

    fun String.stylize(color: List<ColorCode>, reset: Boolean = true): String =
        if (reset) RESET_ALL.code + make(color) + this + RESET_ALL.code
        else make(color) + this

}