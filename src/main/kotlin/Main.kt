
import pt.rafap.tui.datastore.KeyCodes
import pt.rafap.tui.logger.Logger
import pt.rafap.tui.logger.Severity
import pt.rafap.tui.tools.openTerminalIfNotRunning
import pt.rafap.tui.TUI

fun main(){
    openTerminalIfNotRunning()
    TUI
    Logger.log("Starting TUI application...", Severity.INFO)

    while (true) {
        TUI.updateIfNeeded()
        val key = TUI.readKey(true)
        if (key == KeyCodes.ESCAPE) {
            TUI.print("WOW")
        }
        TUI.print(key.code)
    }
}