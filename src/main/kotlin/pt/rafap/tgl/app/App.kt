package pt.rafap.tgl.app

import pt.rafap.tgl.tui.keyboard.KeyCode
import pt.rafap.tgl.utilities.menu.MenuNode

interface App {
    val title: String

    val window: AppWindow

    fun initialize()

    fun inputHandler(input: KeyCode)

    fun run()

    fun update(){
        window.update()
    }

    fun clear(){
        window.clear()
    }

    fun display(){
        window.rePrint()
    }

    fun refresh() {
        window.refresh()
    }

    fun getMenuNodes(): List<MenuNode> {
        return emptyList()
    }
}