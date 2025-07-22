package pt.rafap.tui.datastore

import pt.rafap.tui.datatype.ConfigReader
import pt.rafap.tui.datatype.KeyCode
import java.io.File

object KeyCodes {
    const val PATH = "config/keycodes.properties"
    val config = ConfigReader(File(PATH))

    val F1             = KeyCode(get("F1"))
    val F2             = KeyCode(get("F2"))
    val F3             = KeyCode(get("F3"))
    val F4             = KeyCode(get("F4"))
    val F5             = KeyCode(get("F5"))
    val F6             = KeyCode(get("F6"))
    val F7             = KeyCode(get("F7"))
    val F8             = KeyCode(get("F8"))
    val F9             = KeyCode(get("F9"))
    val F10            = KeyCode(get("F10"))
    val F11            = KeyCode(get("F11"))
    val F12            = KeyCode(get("F12"))

    val UP             = KeyCode(get("UP"))
    val DOWN           = KeyCode(get("DOWN"))
    val LEFT           = KeyCode(get("LEFT"))
    val RIGHT          = KeyCode(get("RIGHT"))

    val ENTER          = KeyCode(get("ENTER"))
    val BACKSPACE      = KeyCode(get("BACKSPACE"))
    val ESCAPE         = KeyCode(get("ESCAPE"))
    val TAB            = KeyCode(get("TAB"))
    val SPACE          = KeyCode(get("SPACE"))
    val DELETE         = KeyCode(get("DELETE"))
    val HOME           = KeyCode(get("HOME"))
    val END            = KeyCode(get("END"))
    val PAGE_UP        = KeyCode(get("PAGE_UP"))
    val PAGE_DOWN      = KeyCode(get("PAGE_DOWN"))
    val INSERT         = KeyCode(get("INSERT"))

    fun get(name: String): Int? {
        return config[name]?.toString()?.toIntOrNull()
    }
}
