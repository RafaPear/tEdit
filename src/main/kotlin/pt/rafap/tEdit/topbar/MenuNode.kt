package pt.rafap.tEdit.topbar

import pt.rafap.tEdit.datastore.Cursor
import pt.rafap.tEdit.tui.TUI
import kotlin.math.max

open class MenuNode(
    val menuTitle: String,
    val children: MutableList<MenuNode> = mutableListOf(),
    var parent: MenuNode? = null,
    var index: Int = -1,
) {
    private val append get() = if (children.isEmpty()) "" else "_"
    val title get() =
        if(level>0) {
            val msg = " $menuTitle$append"
            if (maxSize == 0) maxSize = msg.length + 2
            msg.padEnd(maxSize)
        }
        else {
            val msg = "$menuTitle$append"
            if (maxSize == 0) maxSize = msg.length + 2
            msg.padEnd(maxSize)
        }

    val level: Int
        get() = parent?.level?.plus(1) ?: 0
    val size: Int
        get() = title.length // +2 for the spaces on each side

    var maxSize: Int = 0

    var isOpen: Boolean = false
    var selected: Boolean = false
    var fgColor: String = "BLUE"
    var bgColor: String = "BG_WHITE"
    var highlightColor: String = "WHITE"
    var highlightBgColor: String = "BG_BLUE"
    var effect: String = "NONE"

    var pos = Pair(1, 1)

    // Listener for when the menu is run
    var onRun: (() -> Unit) = {}


    val colors
        get() = if (selected) {
            listOf(highlightColor, highlightBgColor, effect)
        } else {
            listOf(fgColor, bgColor, effect)
        }

    private fun computeSizes() {
        for (child in children) child.computeSizes()
        var m = size
        for (child in children) m = max(m, child.maxSize)
        maxSize = m
        for (child in children) child.maxSize = maxSize
    }

    private fun computePositions() {
        when (level) {
            0 -> {
                pos = Pair(1, 1)
                var x = 1
                val y = pos.second + 1
                for (child in children) {
                    child.pos = Pair(x, y)
                    child.computePositions()
                    x += child.maxSize
                }
            }
            1 -> {
                var y = parent!!.pos.second + 1
                val x = parent!!.pos.first
                for (child in children) {
                    child.pos = Pair(x, y)
                    child.computePositions()
                    y += 1
                }
            }
            else -> {
                var y = parent!!.pos.second
                val x = parent!!.pos.first + parent!!.maxSize
                for (child in children) {
                    child.pos = Pair(x, y)
                    child.computePositions()
                    y += 1
                }
            }
        }
    }

    fun addChild(child: MenuNode) {
        child.parent = this
        children += child
    }

    fun make(){
        computeSizes()
        computePositions()
        for (child in children) {
            child.make()
        }
    }

    fun display() {
        val colors = if (selected) {
            mutableListOf(highlightColor, highlightBgColor, effect)
        } else {
            mutableListOf(fgColor, bgColor, effect)
        }
        Cursor.setPos(pos.first, pos.second)
        TUI.print(title, colors)
    }
}
