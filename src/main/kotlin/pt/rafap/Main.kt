package pt.rafap
import pt.rafap.tgl.tui.TUI
import pt.rafap.tgl.tui.color.Color
import pt.rafap.tgl.tui.tools.openTerminalIfNotRunning
import pt.rafap.tgl.utilities.textbox.box.BoxStyle
import pt.rafap.tgl.utilities.textbox.box.BoxType
import pt.rafap.tgl.utilities.textbox.TextBox
import pt.rafap.tgl.utilities.textbox.TextRectangle
import pt.rafap.tgl.utilities.menu.MenuNode
import pt.rafap.tgl.utilities.menu.MenuTree

fun main() {
    openTerminalIfNotRunning()
    TUI.injectedFunctionExt = { }

    val STYLE = BoxStyle(listOf(Color.BLUE, Color.BG_WHITE))
    val TYPE  = BoxType.DYNAMIC_BOX

    var textBox : TextBox = TextRectangle(
        " Test Rectangle ",
        10, 10,
        TYPE, STYLE
    )

    // Create the top of the tree
    val top = MenuTree(" Test Menu ")

    // -------------------------------
    // MENU FOR TESTING
    // -------------------------------
    val test = MenuNode("Tests..")

    // -------------------------------
    // SUB-MENU FOR TESTING BOXES
    // -------------------------------
    val boxTest = MenuNode("Box Tests..")
    val squareTest = MenuNode("Square Box Test")
    val rectangleTest = MenuNode("Rectangle Box Test")
    val reset = MenuNode("Reset Box")
    val update = MenuNode("Update Box")

    squareTest.onRun = {
        textBox.clear()
        textBox = TextRectangle(
            " Test Square ",
            10, 10,
            BoxType.FIXED_BOX, STYLE
        )
        textBox.refresh(); top.display()
    }
    rectangleTest.onRun = {
        textBox.clear()
        textBox = TextRectangle(
            " Test Rectangle ",
            10, 10,
            TYPE, STYLE
        )
        textBox.refresh(); top.display()
    }
    reset.onRun = {
        textBox.clear()
    }
    update.onRun = {
        textBox.refresh(); top.display()
    }

    boxTest.addChild(squareTest)
    boxTest.addChild(rectangleTest)
    boxTest.addChild(reset)
    boxTest.addChild(update)

    test.addChild(boxTest)

    top.addChild(test)

    top.make()

    top.onUpdate = { textBox.display() }

    top.display()

    while (true) {
        TUI.updateIfNeeded { textBox.refresh() ; top.refresh() }
        val input = TUI.readKey()
        top.handleInput(input)
    }
}