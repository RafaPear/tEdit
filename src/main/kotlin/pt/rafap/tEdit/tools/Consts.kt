package pt.rafap.tEdit.tools

import pt.rafap.tEdit.datatype.ConfigReader
import java.io.File

val codes = ConfigReader(File("config/codes.properties"))

val ESC = codes["ESC"] // Default CSI escape sequence