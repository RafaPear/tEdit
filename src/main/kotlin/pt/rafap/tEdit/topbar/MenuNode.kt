package pt.rafap.tEdit.topbar

import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle
import jdk.javadoc.internal.doclets.toolkit.util.DocPath.parent
import pt.rafap.tEdit.datastore.Cursor
import pt.rafap.tEdit.tui.TUI
import pt.rafap.tEdit.typeExt.center
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

    var childBuffer: MutableList<MenuNode> = mutableListOf()

    val colors
        get() = if (selected) {
            listOf(highlightColor, highlightBgColor, effect)
        } else {
            listOf(fgColor, bgColor, effect)
        }

    fun findMaxSize(): Int {
        var maxSize = this.maxSize
        for (child in childBuffer) {
            val childSize = child.size
            if (childSize > maxSize) {
                maxSize = childSize
            }
        }
        for (child in childBuffer) {
            child.maxSize = maxSize
        }
        this.maxSize = maxSize
        return maxSize
    }

    fun updatePosition(reset: Boolean = false) {
        val parentPos = parent?.pos ?: Pair(1, 1)
        val size = parent?.maxSize ?: 0

        val list = (parent?.children ?: mutableListOf()).toMutableList()
        if (!list.isEmpty()) list.remove(this)

        if (level == 1) {
            var x = 1
            val y = parentPos.second + 1
            if (!list.isEmpty()) {
                val last = list.last()
                x = last.maxSize + last.pos.first
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
            val x = parentPos.first + size
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
        childBuffer += child
    }

    fun make(){

        updatePosition()
        findMaxSize()
        updatePosition()
        for (child in childBuffer) {
            children.add(child)
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