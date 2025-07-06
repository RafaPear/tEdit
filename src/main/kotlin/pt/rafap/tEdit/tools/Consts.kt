package pt.rafap.tEdit.tools

import pt.rafap.tEdit.datastore.Colors
import java.io.File

private val config = GetConfig(File("config/codes.properties"))

val ESC = config["ESC"] // Default CSI escape sequence
val RESET = Colors["RESET"] // Default reset code