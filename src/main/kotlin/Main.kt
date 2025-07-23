
import pt.rafap.tui.TUI
import pt.rafap.tui.datastore.Color
import pt.rafap.tui.datatype.KeyCode
import pt.rafap.tui.logger.Logger
import pt.rafap.tui.logger.Severity
import pt.rafap.tui.tools.openTerminalIfNotRunning
import pt.rafap.tui.utilities.box.BoxStyle
import pt.rafap.tui.utilities.box.TextRectangle
import pt.rafap.tui.utilities.menu.MenuNode
import pt.rafap.tui.utilities.menu.MenuTree
import kotlin.system.exitProcess

fun main() {
    openTerminalIfNotRunning()
    TUI.injectedFunctionExt = { }

    val top = MenuTree("Test Menu")

    val file = MenuNode("File").apply {
        addChild(MenuNode("New").apply {
            onRun = {
                Logger.log("New file created", Severity.INFO)
                top.display()
            }
        })
        addChild(MenuNode("Open").apply {
            onRun = {
                Logger.log("Open file dialog", Severity.INFO)
                top.display()
            }
        })
        addChild(MenuNode("Save").apply {
            onRun = {
                Logger.log("File saved", Severity.INFO)
                top.display()
            }
        })
        addChild(MenuNode("Exit").apply {
            onRun = {
                Logger.log("Exiting application", Severity.INFO)
                TUI.clearAll()
                exitProcess(0)
            }
        })

    }

    val edit = MenuNode("Edit").apply {
        addChild(MenuNode("Undo").apply {
            onRun = {
                Logger.log("Undo action performed", Severity.INFO)
                top.display()
            }
        })
        addChild(MenuNode("Redo").apply {
            onRun = {
                Logger.log("Redo action performed", Severity.INFO)
                top.display()
            }
        })
        addChild(MenuNode("Cut").apply {
            onRun = {
                Logger.log("Cut action performed", Severity.INFO)
                top.display()
            }
        })
        addChild(MenuNode("Copy").apply {
            onRun = {
                Logger.log("Copy action performed", Severity.INFO)
                top.display()
            }
        })
    }

    val help = MenuNode("Help").apply {
        addChild(MenuNode("About").apply {
            onRun = {
                Logger.log("About dialog opened", Severity.INFO)
                top.display()
            }
        })
        addChild(MenuNode("Documentation").apply {
            onRun = {
                Logger.log("Documentation opened", Severity.INFO)
                top.display()
            }
        })
    }

    val keymap = MenuNode("Keymap").apply {
        onRun = {
            Logger.log("Keymap dialog opened", Severity.INFO)
            top.display()
        }
        addChild(MenuNode("View Keymap").apply {
            onRun = {
                Logger.log("Viewing keymap", Severity.INFO)
                top.display()
            }
        })
        addChild(MenuNode("Edit Keymap").apply {
            onRun = {
                Logger.log("Editing keymap", Severity.INFO)
                top.display()
            }
            availableFun = { false } // Example of a disabled option
        })
        addChild(MenuNode("Reset Keymap").apply {
            onRun = {
                Logger.log("Keymap reset to default", Severity.INFO)
                top.display()
            }
        })
        // Example of a sub-menu using availableFun
        addChild(MenuNode("Advanced Keymap").apply {
            onRun = {
                Logger.log("Advanced keymap options opened", Severity.INFO)
                top.display()
            }
            availableFun = { true } // This option is always available
            addChild(MenuNode("Custom Shortcuts").apply {
                onRun = {
                    Logger.log("Custom shortcuts dialog opened", Severity.INFO)
                    top.display()
                }
            })
            addChild(MenuNode("Keymap Settings").apply {
                onRun = {
                    Logger.log("Keymap settings dialog opened", Severity.INFO)
                    top.display()
                }
            })
        })

    }

    val tools = MenuNode("Tools").apply {
        addChild(MenuNode("Calculator").apply {
            onRun = {
                Logger.log("Calculator opened", Severity.INFO)
                top.display()
            }
        })
        addChild(MenuNode("Text Editor").apply {
            onRun = {
                Logger.log("Text Editor opened", Severity.INFO)
                top.display()
            }
        })
        addChild(keymap)
    }

    val settings = MenuNode("Settings").apply {
        addChild(MenuNode("Preferences").apply {
            onRun = {
                Logger.log("Preferences opened", Severity.INFO)
                top.display()
            }
        })
        addChild(MenuNode("Themes").apply {
            onRun = {
                Logger.log("Themes dialog opened", Severity.INFO)
                top.display()
            }
        })
        addChild(MenuNode("Shortcuts").apply {
            onRun = {
                Logger.log("Shortcuts dialog opened", Severity.INFO)
                top.display()
            }
        })
        addChild(tools)
    }

    top.addChild(file)
    top.addChild(edit)
    top.addChild(help)
    top.addChild(settings)

    top.make()

    val textBox = TextRectangle(" Test Rectangle ",
        width = 100,
        height = 10,
        style = BoxStyle(codes = listOf(Color.BLUE, Color.BG_WHITE))
    )

    textBox.initialize()
    top.display()

    while (true) {
        TUI.updateIfNeeded {textBox.refresh() ; top.display()}
        val input = TUI.readKey()
        when (input) {
            in KeyCode('a'.code)..KeyCode('z'.code) -> {
                Logger.log("Received key: $input", Severity.INFO)
                top.display()
            }
            else -> {
                top.handleInput(input)
            }
        }
    }
}