package pt.rafap.tgl.tui.tools

import java.io.File

val codes = ConfigReader(File("config/codes.properties"))

val ESC = codes["ESC"] // Default CSI escape sequence