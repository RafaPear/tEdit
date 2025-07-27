package pt.rafap.tgl.apps.sys

import pt.rafap.tgl.app.App
import pt.rafap.tgl.app.AppWindow
import pt.rafap.tgl.tui.TUI
import pt.rafap.tgl.tui.keyboard.KeyCode

class TestApp(): App {

    override val title: String = "Test App"

    override val window: AppWindow = AppWindow()

    override fun initialize() {
        // Initialization logic here
    }

    override fun inputHandler(input: KeyCode) {
        // Handle input for the TestApp
    }

    override fun run() {
        for (i in 1..10) {
            TUI.writeFooter("Running TestApp iteration $i")
            Thread.sleep(1000) // Simulate some work
        }
    }

    override fun update() {
        // Update logic here
    }

    override fun clear() {
        // Clear logic here
    }

    override fun display() {
        // Display logic here
    }
}