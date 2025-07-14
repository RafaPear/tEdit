package pt.rafap.tEdit.topbar

import pt.rafap.tEdit.datastore.Cursor
import pt.rafap.tEdit.datastore.KeyCodes
import pt.rafap.tEdit.datatype.KeyCode
import pt.rafap.tEdit.tui.TUI
import pt.rafap.tEdit.tui.TUI.updateIfNeeded
import pt.rafap.tEdit.typeExt.center

class MenuTree(
    title: String,
    children: MutableList<MenuNode> = mutableListOf()
) {
    private val root: MenuNode = MenuNode(title, children, null)

    private var current = root

    init {
        root.isOpen = true
    }

    fun addChild(child: MenuNode) {
        root.addChild(child)
    }

    fun make(){
        root.make()
        root.updatePositionsRecursive()
    }

    fun display(){
        TUI.buffered = false
        Cursor.saveToBuffer()
        TUI.writeHeader(root.title.center(Cursor.bounds.second, ' '), root.colors)
        displayChildren()
        Cursor.restoreFromBuffer()
        TUI.buffered = true
    }

    private fun deleteEmpty(root: MenuNode){
        for (grandChild in root.children) {
            if (grandChild.level > 0 && !grandChild.isOpen) {
                Cursor.setPos(grandChild.pos.first, grandChild.pos.second)
                TUI.clearLine()
            }
        }
        Thread.sleep(200)
    }

    fun displayChildren(root: MenuNode? = this.root, deleteEmpty: Boolean = false) {
        if (root == null) return
        if (root.children.isEmpty()) return

        for (i in root.children.indices) {
            val child = root.children[i]
            if (deleteEmpty) deleteEmpty(child)
            child.display()
            if (child.isOpen) {
                displayChildren(child, true)
            }
        }
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
        if (input == KeyCodes.DOWN && current.level == 0) {
            enter()
            return
        }
        if (input == KeyCodes.LEFT && current.level > 0)   {
            back()
            return
        }

        if (current.index == -1) current.index = 1
        current.children[current.index].apply {
            isOpen = false
            selected = false
        }
        current.index--
        if (current.index < 0) current.index = current.children.size - 1
        current.index %= current.children.size
        current.children[current.index].apply {
            isOpen = true
            selected = true
        }
        displayChildren(current, true)
    }

    private fun moveToNext(input: KeyCode) {
        if (input == KeyCodes.UP && current.level == 0){
            back()
            return
        }
        else if (input == KeyCodes.RIGHT && current.level > 0){
            enter()
            return
        }

        if (current.index >= 0)
            current.children[current.index].apply {
                isOpen = false
                selected = false
            }
        current.index++
        current.index %= current.children.size
        current.children[current.index].apply {
            isOpen = true
            selected = true
        }
        displayChildren(current, true)
    }

    private fun back() {
        if (current.index == -1) return
        if (current.level > 0) {
            current.children[current.index].apply {
                isOpen = false
                selected = false
            }
            current = current.parent ?: root
        }
        displayChildren(current)
    }

    private fun enter(){
        if (current.index == -1) return
        if (!current.children[current.index].children.isEmpty())
            current = current.children[current.index]
        else {
            current.children[current.index].onRun()
            return
        }
        current.index = 0
        current.children[current.index].apply {
            isOpen = true
            selected = true
        }
        displayChildren(current)
    }
}