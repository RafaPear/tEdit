package pt.rafap.tgl.tui.tools

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.Wincon
import pt.rafap.tgl.tui.keyboard.RawConsoleInput
import pt.rafap.tgl.tui.logger.Logger
import pt.rafap.tgl.tui.logger.Severity
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

/** Devolve (linhas, colunas) do terminal actual ou null se não conseguir determinar. */
private val UNKNOWN: Pair<Int, Int> = -1 to -1

private interface Kernel32 : Library {
    fun GetStdHandle(nStdHandle: Int): Pointer
    fun GetConsoleScreenBufferInfo(hConsoleOutput: Pointer, info: Wincon.CONSOLE_SCREEN_BUFFER_INFO
    ): Boolean
    companion object { val INSTANCE = Native.load("kernel32", Kernel32::class.java) }
}

private fun winSize(): Pair<Int, Int> {
    val hOut = Kernel32.INSTANCE.GetStdHandle(Wincon.STD_OUTPUT_HANDLE)
    val info = Wincon.CONSOLE_SCREEN_BUFFER_INFO()
    return if (Kernel32.INSTANCE.GetConsoleScreenBufferInfo(hOut, info)) {
        val rows = (info.srWindow.Bottom - info.srWindow.Top + 1)
        val cols = (info.srWindow.Right  - info.srWindow.Left + 1)
        rows to cols
    } else UNKNOWN
}

private fun unixSize(): Pair<Int, Int>? =
    try {
        val p = ProcessBuilder("sh", "-c", "stty size < /dev/tty")
            .redirectErrorStream(true).start()
        val out = p.inputStream.bufferedReader().readText().trim()
        p.waitFor(500, TimeUnit.MILLISECONDS)
        out.split(Regex("\\s+"))
            .mapNotNull { it.toIntOrNull() }
            .takeIf { it.size == 2 }
            ?.let { it[0] to it[1] }
    } catch (_: Exception) { null }

private fun tputSize(): Pair<Int, Int>? =
    try {
        val rows = Runtime.getRuntime()
            .exec(arrayOf("sh", "-c", "tput lines"))
            .inputStream.bufferedReader().readText().trim().toIntOrNull()
        val cols = Runtime.getRuntime()
            .exec(arrayOf("sh", "-c", "tput cols"))
            .inputStream.bufferedReader().readText().trim().toIntOrNull()
        if (rows != null && cols != null) rows to cols else null
    } catch (_: Exception) { null }

fun getTerminalSize(): Pair<Int, Int> {
    val os = System.getProperty("os.name")
    var final = if (os.startsWith("Windows", true))
        winSize()
    else
        unixSize() ?: tputSize() ?: UNKNOWN
    if (final.first == 0)
        final = final.copy(first = 1) // Default to 24 rows if size is zero
    if (final.second == 0)
        final = final.copy(second = 1) // Default to 80 columns if size is zero
    return final.first + 1 to final.second + 1
}

/** Abre uma nova janela de terminal a correr a aplicação actual. */
private fun openExternalTerminal(): Boolean {
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

fun openTerminalIfNotRunning() {
    if (!isRunningInTerminal()) if (!openExternalTerminal()) Logger.log(
        "Failed to open external terminal.", Severity.ERROR
    )
    else exitProcess(0)
}
