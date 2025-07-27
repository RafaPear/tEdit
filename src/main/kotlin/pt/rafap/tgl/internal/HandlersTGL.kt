package pt.rafap.tgl.internal

import pt.rafap.tgl.TGL.MENU_TREE
import pt.rafap.tgl.TGL.currentApp
import pt.rafap.tgl.TGL.isInMenu
import pt.rafap.tgl.tui.keyboard.KeyCode

internal fun escapeHandler() {
    // If in menu, exit the app or return to the main menu
    isInMenu = !isInMenu
    if (!isInMenu) MENU_TREE.returnFromAll()
}

internal fun otherKeysHandler(input: KeyCode) {
    if (isInMenu) MENU_TREE.inputHandler(input)
    else currentApp?.inputHandler(input)
}