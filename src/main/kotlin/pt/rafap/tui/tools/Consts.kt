package pt.rafap.tui.tools

import pt.rafap.tui.datatype.ConfigReader
import java.io.File

val codes = ConfigReader(File("config/codes.properties"))

val ESC = codes["ESC"] // Default CSI escape sequence