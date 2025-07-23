
import pt.rafap.tui.TUI
import pt.rafap.tui.datastore.Color
import pt.rafap.tui.tools.openTerminalIfNotRunning
import pt.rafap.tui.utilities.box.BoxStyle
import pt.rafap.tui.utilities.box.TextBox
import pt.rafap.tui.utilities.box.TextRectangle
import pt.rafap.tui.utilities.box.TextSquare
import pt.rafap.tui.utilities.menu.MenuNode
import pt.rafap.tui.utilities.menu.MenuTree

fun main() {
    openTerminalIfNotRunning()
    TUI.injectedFunctionExt = { }

    var textBox : TextBox = TextRectangle(
        " Test Rectangle ",
        width = 100,
        height = 10,
        style = BoxStyle(codes = listOf(Color.BLUE, Color.BG_WHITE))
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
        textBox = TextSquare(
            " Test Square ",
            30,
            BoxStyle(listOf(Color.BLUE, Color.BG_WHITE))
        )
        textBox.refresh(); top.display()
    }
    rectangleTest.onRun = {
        textBox.clear()
        textBox = TextRectangle(
            " Test Rectangle ",
            100,
            10,
            BoxStyle(listOf(Color.BLUE, Color.BG_WHITE))
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

    textBox.initialize()
    top.display()

    while (true) {
        TUI.updateIfNeeded { textBox.refresh(); top.display() }
        val input = TUI.readKey()
        top.handleInput(input)
    }
}