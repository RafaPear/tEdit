package pt.rafap.tgl

import pt.rafap.tgl.app.App
import pt.rafap.tgl.coroutine.CoroutineManager
import pt.rafap.tgl.internal.coroutines.KeyInputCoroutine
import pt.rafap.tgl.internal.coroutines.ResizeCoroutine
import pt.rafap.tgl.internal.coroutines.destroyAPP
import pt.rafap.tgl.internal.coroutines.summonAPP
import pt.rafap.tgl.internal.escapeHandler
import pt.rafap.tgl.internal.initializeMenu
import pt.rafap.tgl.internal.otherKeysHandler
import pt.rafap.tgl.tui.TUI
import pt.rafap.tgl.tui.keyboard.Key
import pt.rafap.tgl.tui.keyboard.KeyCode
import pt.rafap.tgl.tui.logger.Logger
import pt.rafap.tgl.tui.logger.Severity
import pt.rafap.tgl.tui.tools.openTerminalIfNotRunning
import pt.rafap.tgl.utilities.menu.MenuNode
import pt.rafap.tgl.utilities.menu.MenuStyle
import pt.rafap.tgl.utilities.menu.MenuTree
import kotlin.system.exitProcess

// TODO: App menu as start screen
object TGL {

    // Title Definitions
    var defaultTitle: String = "TGL - Terminal Graphics Library"
    internal var title: String = defaultTitle

    // Menu Definitions
    internal var defaultSystemTitle: String = "System"

    internal var MENU_STYLE: MenuStyle = MenuStyle()
    internal var MENU_TREE: MenuTree = MenuTree(title, MENU_STYLE)
    internal var SYSTEM: MenuNode = MenuNode(defaultSystemTitle, MENU_STYLE)

    // Current App Context
    internal var currentApp: App? = null

    // System Context
    internal var isInMenu: Boolean = false
    internal val onUpdateFun: () -> Unit
        get() = { MENU_TREE.refresh() }

    internal var coroutines = listOf(
        KeyInputCoroutine,
        ResizeCoroutine
    )

    fun init() {
        openTerminalIfNotRunning()

        // Initialize the menu
        Logger.log("Initializing Menu", Severity.INFO)
        initializeMenu()

        // Create threads
        Logger.log("Creating Threads", Severity.INFO)
        createCoroutines()

        // Start threads
        Logger.log("Starting Threads", Severity.INFO)
        startCoroutines()
    }

    fun exit() {
        stopCoroutines() // Stop threads

        TUI.clearAll() // Clear Screen

        CoroutineManager.stopAllCoroutines() // Stop all threads

        Logger.writeToFile("tgl.log") // Print log to file

        exitProcess(0) // Exit the application
    }

    fun appRun(app: App) {
        // Set the current app context
        currentApp = app

        // Initialize the app and its menu
        summonAPP(app)

        // Initialize the menu with the app's title and nodes
        initializeMenu(app)

        // Run the app
        app.run()

        // Restart threads to ensure they are in sync with the new app context
        restartCoroutines()

        // Reset the current app context after running
        destroyAPP(app)
        currentApp = null
        title = defaultTitle
    }

    fun update() {
        // Update the menu tree
        MENU_TREE.refresh()
    }

    internal fun resizeHandler() {
        // Handle terminal resize events
        TUI.updateIfNeeded { onUpdateFun() }
        Logger.log("Terminal resized", Severity.INFO)
    }

    internal fun inputHandler(input: KeyCode) {
        when (input) {
            Key.ESCAPE -> escapeHandler()
            else -> otherKeysHandler(input)
        }
    }

    internal fun startCoroutines() =
        coroutines.forEach { it.start() }

    internal fun stopCoroutines() =
        coroutines.forEach { it.stop() }

    internal fun deleteCoroutines() =
        coroutines.forEach { it.delete() }

    internal fun createCoroutines() =
        coroutines.forEach { it.create() }

    internal fun restartCoroutines() {
        stopCoroutines() // Stop all coroutines
        deleteCoroutines()
        createCoroutines()
        startCoroutines() // Start all coroutines again
    }
}