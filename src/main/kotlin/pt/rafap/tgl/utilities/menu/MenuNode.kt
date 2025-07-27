package pt.rafap.tgl.utilities.menu

import pt.rafap.tgl.tui.TUI
import pt.rafap.tgl.tui.color.Color
import pt.rafap.tgl.tui.cursor.Cursor
import pt.rafap.tgl.tui.logger.Logger
import pt.rafap.tgl.tui.logger.Severity
import pt.rafap.tgl.tui.typeExt.center

class MenuNode(
    var title: String,
    color: MenuStyle = MenuStyle(),
    startIndex: Int = 0,
) {
    // Node children
    val children: MutableList<MenuNode> = mutableListOf()
    private var childBuffer: MutableList<MenuNode> = mutableListOf()

    // Parent Node
    var parent: MenuNode? = null

    // This Node
    private val node get() = this

    // Tree Level
    val level: Int
        get() = parent?.level?.plus(1) ?: 0

    // Horizontal block size
    var size: Int = title.length + 2

    // Current index
    var index: Int = startIndex
        set(value) {
            close(field)
            var temp = value
            if (temp < 0) temp += children.size
            temp %= children.size
            open(temp)
            field = temp
        }

    // Current selected child by the index
    val child get() = children[index]

    // State
    var isOpen: Boolean = false
        set(value) {
            if (children.isNotEmpty() || level == 0){
                prevOpen = field
                field = value
            }
        }
    var prevOpen: Boolean = false
    var isSelected: Boolean = false
    val isShown: Boolean
        get() = parent?.isOpen ?: true

    // Colors
    var colorPreset: MenuStyle = parent?.colorPreset ?: color // Default Preset
    val colors
        get() = colorPreset[this]

    // Node Position
    var pos = Pair(1, 1)

    // Listener for when the menu is run
    var onRun: (() -> Unit) = {}

    // Function that is run to check if this node is enabled to display ot not
    var availableFun: (() -> Boolean) = { true }

    fun open(id: Int = index) {
        if (index == -1) return
        children[id].apply {
            isOpen = true
            isSelected = true
        }
    }

    fun close(id: Int = index) {
        if (index >= 0) children[id].apply {
            isOpen = false
            isSelected = false
        }
    }

    fun enter(): MenuNode = if (!child.children.isEmpty()) {
        child.index = -1
        child.incIndex()
        child
    } else {
        if (child.availableFun()) child.onRun()
        this
    }

    fun exit(): MenuNode =
        if (level >= 0) {
            close()
            parent ?: this
        } else this

    fun incIndex() {
        index++
        if (!child.availableFun()) incIndex()
    }

    fun decIndex() {
        index--
        // Decrement again if current child is unavailable
        if (!child.availableFun()) decIndex()
    }

    fun findMaxSize() {
        var maxSize = 0
        if (level < 2 && level > 0) {
            maxSize = size
        }
        for (child in children + childBuffer) {
            child.size = child.title.length + 2
            val childSize = child.size
            if (childSize > maxSize) {
                maxSize = childSize
            }
        }
        for (child in children) {
            child.size = maxSize
        }
    }

    // TODO Melhorar calculo de posição.
    fun updatePosition() {
        val parentPos = parent?.pos ?: Pair(1, 1)
        val parentSize = parent?.size ?: 0

        // Copy of the parent children and remove this node to not take
        // in count for calculations
        val parentChildren = (parent?.children ?: mutableListOf()).toMutableList().apply { remove(node) }

        // Get the last children from the parent if it exists, else null
        val last: MenuNode? = try {
            parentChildren.last()
        } catch (_: Exception) {
            null
        }

        // Reset the current size
        pos = Pair(1, 1)

        when (level) {
            1 -> {
                pos = if (last == null) Pair(1, parentPos.second + 1)
                else Pair(last.size + last.pos.first, parentPos.second + 1)
            }

            2 -> {
                pos = if (last == null) Pair(parentPos.first, parentPos.second + 1)
                else Pair(parentPos.first, last.pos.second + 1)
            }

            else -> {
                pos = if (level > 2) if (last == null) Pair(parentPos.first + parentSize, parentPos.second)
                else Pair(parentPos.first + parentSize, last.pos.second + 1)
                else Pair(1, 1)
            }
        }
    }

    fun addChild(child: MenuNode) {
        child.parent = this
        childBuffer += child
    }

    fun make() {
        for (child in childBuffer) {
            children.add(child)
            Logger.log("Added child '${child.title}' to '${title}' children at level $level", Severity.DEBUG)
            if (level > 0) findMaxSize()
            child.updatePosition()
            child.make()
        }
        childBuffer.clear()
    }

    fun display() {
        Cursor.setPos(pos.first, pos.second)
        if (!isShown) TUI.print(" ".padEnd(size), listOf(Color.BG_BLACK, Color.BLACK))
        //else if (prevOpen && !isOpen && level > 1) TUI.print(" ".padEnd(size), listOf(Color.BG_BLACK, Color.BLACK))
        else if (level > 1) TUI.print(title.padEnd(size), colors)
        else TUI.print(title.center(size), colors)
    }
}