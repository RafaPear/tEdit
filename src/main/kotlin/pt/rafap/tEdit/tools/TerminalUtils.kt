package pt.rafap.tEdit.tools

import pt.rafap.tEdit.logger.Logger
import pt.rafap.tEdit.logger.Severity
import pt.rafap.tEdit.ext.RawConsoleInput
import java.nio.file.Paths

/** Opens a new terminal window running the current application. */
fun openExternalTerminal(): Boolean {
    RawConsoleInput.resetConsoleMode()
    if (isRunningInTerminal()) {
        Logger.log("Cannot open terminal from within a terminal session.", Severity.ERROR)
        return false
    }
    val javaHome = System.getProperty("java.home")
    val javaBin = Paths.get(javaHome, "bin", "java").toString()
    val classPath = System.getProperty("java.class.path")
    val command = System.getProperty("sun.java.command")
    val exec = "\"$javaBin\" -cp \"$classPath\" $command"
    val os = System.getProperty("os.name").lowercase()

    val builder = when {
        os.contains("win") -> ProcessBuilder("cmd", "/c", "start", "cmd", "/k", exec)
        os.contains("mac") -> ProcessBuilder("osascript", "-e", "tell application \"Terminal\" to do script \"$exec\"")
        else -> ProcessBuilder("x-terminal-emulator", "-e", exec)
    }


    return try {
        builder.start()
        true
    } catch (e: Exception) {
        Logger.log("Unable to open terminal: ${e.message}", Severity.ERROR)
        false
    }
}

fun isRunningInTerminal(): Boolean = System.console() != null
