package pt.rafap.tui.utilities.menu

import pt.rafap.tui.TUI
import pt.rafap.tui.TUI.updateIfNeeded
import pt.rafap.tui.datastore.Cursor
import pt.rafap.tui.datastore.KeyCodes
import pt.rafap.tui.datatype.KeyCode
import pt.rafap.tui.typeExt.center

// TODO Passar entrada de cores para MenuTree em vez de cada node ter a sua cor

class MenuTree(
    title: String,
    color: MenuColor = MenuColor()
) {
    private val root: MenuNode = MenuNode(title)

    private val centeredTitle get() = root.title.center(Cursor.bounds.second, ' ')

    private var current = root

    init {
        root.isOpen = true
        root.colorPreset = color
    }

    fun addChild(child: MenuNode) {
        root.addChild(child)
    }

    fun make(){
        root.make()
    }

    fun display(){
        Cursor.runWithoutChange {
            TUI.writeHeader(centeredTitle, root.colors)
            displayChildren()
        }
    }

    private fun deleteEmpty(root: MenuNode){
        for (child in root.children) {
            deleteEmpty(child)
            child.display()
        }
    }

    fun displayAll(root: MenuNode? = current) {
        if (root == null) return

        for (child in root.children) {
            if (child.isOpen) displayAll(child)
            child.display()
        }
    }

    fun displayChildren(clear: Boolean = true) {
        if (clear) deleteEmpty(current)
        displayAll(current)
    }

    fun handleInput(input: KeyCode) {
        updateIfNeeded { display() }
        when (input) {
            KeyCodes.LEFT, KeyCodes.DOWN -> { moveToPrevious(input) }
            KeyCodes.RIGHT, KeyCodes.UP  -> { moveToNext(input) }
            KeyCodes.ESCAPE ->  { back() }
            KeyCodes.ENTER  ->  { enter() }
        }
    }

    private fun moveToPrevious(input: KeyCode) {
        if (input == KeyCodes.DOWN && current.level == 0 && current.child.isSelected) enter()
        else if (input == KeyCodes.LEFT && current.level > 0) back()
        else if (input == KeyCodes.DOWN && current.level > 0) incIndex()
        else decIndex()
    }

    private fun moveToNext(input: KeyCode) {
        if (input == KeyCodes.UP && current.level == 0) back()
        else if (input == KeyCodes.RIGHT && current.level > 0) enter()
        else if (input == KeyCodes.UP && current.level > 0) decIndex()
        else incIndex()
    }

    private fun incIndex(){
        current.incIndex()
        displayChildren()
    }

    private fun decIndex(){
        current.decIndex()
        displayChildren()
    }

    private fun back() {
        current = current.exit()
        displayChildren()
    }

    private fun enter(){
        current = current.enter()
        displayChildren()
    }
}