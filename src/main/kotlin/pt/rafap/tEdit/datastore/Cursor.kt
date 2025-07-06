package pt.isel.datastore

import pt.isel.tools.GetConfig
import java.io.File

object Cursor {
    var x: Int = 0
    var y: Int = 0
    var visible: Boolean = true
    var color: String = "30"
    var backgroundColor: String = "black"
    var blink: Boolean = false
    var blinkRate: Int = 500 // in milliseconds

    init {
        try {
            val configFile = File("config/cursor.properties")
            val properties = GetConfig(configFile)
            color = properties["color"]?.toString() ?: "white"
            backgroundColor = properties["backgroundColor"]?.toString() ?: "black"
            blinkRate = properties["blinkRate"]?.toString()?.toIntOrNull() ?: 500

        }
        catch (e: Exception) {
            println("Error loading cursor configuration: ${e.message}")
        }
    }
}