package pt.rafap.tEdit.topbar

import pt.rafap.tEdit.datastore.TextBuffer
import pt.rafap.tEdit.datatype.KeyCode
import pt.rafap.tEdit.logger.Logger
import pt.rafap.tEdit.logger.Severity
import pt.rafap.tEdit.tools.openTerminalIfNotRunning
import pt.rafap.tEdit.tui.TUI
import kotlin.system.exitProcess

fun main() {
    openTerminalIfNotRunning()
    Logger.severity = Severity.INFO
    val tree = MenuTree("Test Menu")

    val file = MenuNode("File") .apply {
        onRun = {
            Logger.log("File menu selected", Severity.INFO)
        }
    }
    val edit = MenuNode("Edit")
    val view = MenuNode("View")

    val exit = MenuNode("Exit").apply {
        onRun = {
            Logger.log("Exiting...", Severity.INFO)
            TUI.clearAll()
            exitProcess(0)
        }
    }

    val hello = MenuNode("Hellooooo")

    val test = MenuNode("Bueda fixe")
    val test2 = MenuNode("Test2")
    val test3 = MenuNode("Test3")
    val test4 = MenuNode("Test4")

    file.addChild(exit)
    file.addChild(hello)

    exit.addChild(test3)
    exit.addChild(test4)

    hello.addChild(test)
    hello.addChild(test2)

    tree.addChild(edit)
    tree.addChild(file)
    tree.addChild(view)

    tree.make()

    tree.display()
    while (true) {
        TUI.updateIfNeeded {
            tree.display()
        }
        val input = TUI.readKey()
        tree.handleInput(input)
    }
}