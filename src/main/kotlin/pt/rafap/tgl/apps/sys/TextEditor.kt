package pt.rafap.tgl.apps.sys

import pt.rafap.tgl.app.App
import pt.rafap.tgl.app.AppWindow
import pt.rafap.tgl.tui.cursor.Cursor
import pt.rafap.tgl.tui.keyboard.KeyCode
import pt.rafap.tgl.utilities.menu.MenuNode

class TextEditor() : App {

    override val title: String = "Text Editor"
    override val window: AppWindow = AppWindow()

    val textBuffer
        get() = window.tb

    override fun initialize() {
        Cursor.isVisible = true
    }

    override fun inputHandler(input: KeyCode) {
        when (input) {
            /*Key.BACKSPACE -> {
                textBuffer.deleteChar()
            }
            in KeyCode(0x20)..KeyCode(0x7E) -> {
                textBuffer.setChar(input.code?.toChar() ?: '?')
            }*/
        }
    }

    override fun run() {
        while (true) {
            window.display()
        }
    }

    override fun getMenuNodes(): List<MenuNode> {
        val FILE = MenuNode("File")
        val EDIT = MenuNode("Edit")
        val VIEW = MenuNode("View")
        val DEBUG = MenuNode("Debug")

        // Add sub-menus to the File menu
        val newFile = MenuNode("New").apply {
            onRun = { /* Logic to create a new file */ }
        }
        val openFile = MenuNode("Open").apply {
            onRun = { /* Logic to open a file */ }
        }
        val saveFile = MenuNode("Save").apply {
            onRun = { /* Logic to save the current file */ }
        }
        val exit = MenuNode("Exit").apply {
            onRun = { /* Logic to exit the text editor */ }
        }

        // Add sub-menus to the Edit menu
        val cut = MenuNode("Cut").apply {
            onRun = { /* Logic to cut selected text */ }
        }
        val copy = MenuNode("Copy").apply {
            onRun = { /* Logic to copy selected text */ }
        }

        val paste = MenuNode("Paste").apply {
            onRun = { /* Logic to paste text from clipboard */ }
        }

        // Add sub-menus to the View menu
        val syntHigh = MenuNode("Syntax Highlighting").apply {
            onRun = { /* Logic to toggle syntax highlighting */ }
        }

        val lineNumbers = MenuNode("Line Numbers").apply {
            onRun = { /* Logic to toggle line numbers */ }
        }

        // Add sub-menus to the Debug menu
        val refresh = MenuNode("Refresh").apply {
            onRun = { refresh() }
        }
        val clear = MenuNode("Clear").apply {
            onRun = { clear() }
        }
        val display = MenuNode("Display").apply {
            onRun = { display() }
        }

        // Add children to the File menu
        FILE.addChild(newFile)
        FILE.addChild(openFile)
        FILE.addChild(saveFile)
        FILE.addChild(exit)

        // Add children to the Edit menu
        EDIT.addChild(cut)
        EDIT.addChild(copy)
        EDIT.addChild(paste)

        // Add children to the View menu
        VIEW.addChild(syntHigh)
        VIEW.addChild(lineNumbers)

        // Add children to the Debug menu
        DEBUG.addChild(refresh)
        DEBUG.addChild(clear)
        DEBUG.addChild(display)

        // Return the list of menu nodes
        return listOf(FILE, EDIT, VIEW, DEBUG)
    }
}