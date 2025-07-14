
import pt.rafap.tEdit.datastore.KeyCodes
import pt.rafap.tEdit.logger.Logger
import pt.rafap.tEdit.logger.Severity
import pt.rafap.tEdit.tools.openTerminalIfNotRunning
import pt.rafap.tEdit.tui.TUI

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