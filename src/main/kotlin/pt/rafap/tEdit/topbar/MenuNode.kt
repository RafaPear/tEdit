package pt.rafap.tEdit.topbar

import jdk.internal.vm.ThreadContainers.root
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle
import jdk.javadoc.internal.doclets.toolkit.util.DocPath.parent
import org.jline.terminal.Size
import pt.rafap.tEdit.datastore.Colors.stylize
import pt.rafap.tEdit.datastore.Cursor
import pt.rafap.tEdit.tui.TUI
import pt.rafap.tEdit.typeExt.center
import java.awt.Menu
import kotlin.math.max

open class MenuNode(
    val menuTitle: String,
    val children: MutableList<MenuNode> = mutableListOf(),
    var parent: MenuNode? = null,
    var index: Int = -1,
) {
    val title get() = menuTitle
    val level: Int
        get() = parent?.level?.plus(1) ?: 0
    var size: Int = title.length + 2

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

    var childBufer: MutableList<MenuNode> = mutableListOf()

    val colors
        get() = if (selected) {
            listOf(highlightColor, highlightBgColor, effect)
        } else {
            listOf(fgColor, bgColor, effect)
        }

    fun maxChildCount(): Int {
        if (children.isEmpty()) return 0
        var maxCount = 0
        for (child in children) {
            val childCount = child.children.size
            if (childCount > maxCount) {
                maxCount = childCount
            }
        }
        return maxCount
    }

    fun findMaxSize(): Int {
        var maxSize = 0
        if (level < 2 && level > 0) {
            maxSize = size
        }
        for (child in children) {
            val childSize = child.size
            if (childSize > maxSize) {
                maxSize = childSize
            }
        }
        for (child in children) {
            child.size = maxSize
        }
        return maxSize
    }

    fun updatePosition(reset: Boolean = false) {
        val parentPos = parent?.pos ?: Pair(1, 1)
        val size = parent?.size ?: 0

        val list = (parent?.children ?: mutableListOf()).toMutableList()
        if (!list.isEmpty()) list.remove(this)

        if (level == 1) {
            var x = 1
            val y = parentPos.second + 1
            if (!list.isEmpty()) {
                val last = list.last()
                x = last.size + last.pos.first
            }
            pos = Pair(x, y)
            return
        } else if (level == 2) {
            val x = parentPos.first
            var y = parentPos.second + 1
            if (!list.isEmpty()) {
                val last = list.last()
                y = last.pos.second + 1
            }
            pos = Pair(x, y)
            return
        }
        else if (level > 2) {
            val x = parentPos.first + size - 1
            var y = parentPos.second
            if (!list.isEmpty()) {
                val last = list.last()
                y = last.pos.second + 1
            }
            pos = Pair(x, y)
            return
        }
        pos = Pair(1, 1)
    }

    fun addChild(child: MenuNode) {
        child.parent = this
        childBufer += child
    }

    fun make(){
        for (child in childBufer) {
            children.add(child)
            if (level > 0) {
                child.size = findMaxSize()
                findMaxSize()
            }
            child.updatePosition()
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
        if (level > 1)
            TUI.print(title.padEnd(size-1), colors)
        else
            TUI.print(title.center(size), colors)
    }
}