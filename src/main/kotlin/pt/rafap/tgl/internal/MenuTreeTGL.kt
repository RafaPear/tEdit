package pt.rafap.tgl.internal

import pt.rafap.tgl.TGL
import pt.rafap.tgl.TGL.MENU_STYLE
import pt.rafap.tgl.TGL.MENU_TREE
import pt.rafap.tgl.TGL.SYSTEM
import pt.rafap.tgl.TGL.defaultSystemTitle
import pt.rafap.tgl.TGL.title
import pt.rafap.tgl.app.App
import pt.rafap.tgl.utilities.menu.MenuNode
import pt.rafap.tgl.utilities.menu.MenuTree


internal fun initializeMenu(app: App? = null) {
    // Pre-Processing
    val menuTitle = app?.title ?: title
    val appMenuNodes = app?.getMenuNodes() ?: emptyList()
    val updateFun: () -> Unit =
        if (app != null) {
            { app.display() }
        } else {
            { }
        }

    // Updates the system menu entry
    makeSystemMenu()

    // Resets the menu tree with the new title and system menu
    MENU_TREE = MenuTree(menuTitle, MENU_STYLE)
    MENU_TREE.onUpdate = updateFun
    MENU_TREE.addChild(SYSTEM)

    // Adds the app's menu nodes to the menu tree
    appMenuNodes.forEach {
        MENU_TREE.addChild(it)
    }

    // Finalize and Display the menu tree
    MENU_TREE.make()
    MENU_TREE.display()
}

internal fun makeSystemMenu() {
    // MENU
    SYSTEM = MenuNode(defaultSystemTitle, MENU_STYLE)

    // SUB-MENUs
    val exit = MenuNode("Exit", MENU_STYLE).apply {
        onRun = { TGL.exit() }
    }
    val clear = MenuNode("Clear", MENU_STYLE)
    val refresh = MenuNode("Refresh", MENU_STYLE)
    val help = MenuNode("Help", MENU_STYLE)
    val about = MenuNode("About", MENU_STYLE)

    // Add sub-menus to the system menu
    SYSTEM.addChild(exit)
    SYSTEM.addChild(clear)
    SYSTEM.addChild(refresh)
    SYSTEM.addChild(help)
    SYSTEM.addChild(about)
}