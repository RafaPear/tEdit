import pt.rafap.tEdit.datastore.Colors.stylize
import pt.rafap.tEdit.datastore.Cursor
import pt.rafap.tEdit.logger.Logger
import pt.rafap.tEdit.logger.Severity
import pt.rafap.tEdit.tools.RESET
import pt.rafap.tEdit.tools.isRunningInTerminal
import pt.rafap.tEdit.tools.openExternalTerminal

fun main() {
    openExternalTerminal()
    if (!isRunningInTerminal()) return
    Logger.log("This is a test message", Severity.INFO)
    Cursor.show()
    Cursor.resetPos()
    while (true) {

    }
}