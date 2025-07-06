import pt.isel.datastore.Colors
import pt.rafap.tEdit.tools.isRunningInTerminal
import pt.rafap.tEdit.tools.openExternalTerminal

fun main() {
    openExternalTerminal()
    if (!isRunningInTerminal()) return
    val sequence = listOf("RED", "BOLD", "UNDERLINE")
    print("${Colors.make(sequence)}HELLO WORLD${Colors["RESET"]}")
}