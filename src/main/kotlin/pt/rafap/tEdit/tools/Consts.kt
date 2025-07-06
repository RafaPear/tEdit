package pt.isel.tools

import pt.isel.datastore.Colors
import java.io.File

private val config = GetConfig(File("config/codes.properties"))

val ESC = config["ESC"]
val RESET = Colors["RESET"]