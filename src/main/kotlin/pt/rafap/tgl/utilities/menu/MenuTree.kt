package pt.rafap.tgl.utilities.menu

import pt.rafap.tgl.tui.TUI
import pt.rafap.tgl.tui.cursor.Cursor
import pt.rafap.tgl.tui.keyboard.Key
import pt.rafap.tgl.tui.keyboard.KeyCode
import pt.rafap.tgl.tui.typeExt.center

// TODO Passar entrada de cores para MenuTree em vez de cada node ter a sua cor

class MenuTree(
    title: String,
    color: MenuColor = MenuColor()
) {
    private val root: MenuNode = MenuNode(title)

    private val centeredTitle get() = root.title.center(Cursor.bounds.second, ' ')

    private var current = root

    var onUpdate = { }

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

    fun refresh() {
        Cursor.runWithoutChange {
            TUI.writeHeader(centeredTitle, root.colors)
            displayChildren(root = root)
        }
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

    fun displayChildren(clear: Boolean = true, root: MenuNode = current) {
        if (clear) deleteEmpty(root)
        onUpdate()
        displayAll(root)
    }

    fun handleInput(input: KeyCode) {
        when (input) {
            Key.LEFT, Key.DOWN -> { moveToPrevious(input) }
            Key.RIGHT, Key.UP  -> { moveToNext(input) }
            Key.ESCAPE ->  { back() }
            Key.ENTER  ->  { enter() }
        }
    }

    private fun moveToPrevious(input: KeyCode) {
        if (input == Key.DOWN && current.level == 0 && current.child.isSelected) enter()
        else if (input == Key.LEFT && current.level > 0) back()
        else if (input == Key.DOWN && current.level > 0) incIndex()
        else decIndex()
    }

    private fun moveToNext(input: KeyCode) {
        if (input == Key.UP && current.level == 0) back()
        else if (input == Key.RIGHT && current.level > 0) enter()
        else if (input == Key.UP && current.level > 0) decIndex()
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