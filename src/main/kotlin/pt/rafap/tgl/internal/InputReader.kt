package pt.rafap.tgl.internal

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import pt.rafap.tgl.tui.TUI
import pt.rafap.tgl.tui.keyboard.KeyCode

object InputReader {

    private var scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var keyChannel = Channel<KeyCode>(capacity = Channel.UNLIMITED)

    fun startReading() {
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        keyChannel = Channel(capacity = Channel.UNLIMITED)
        scope.launch {
            while (isActive) {
                val key = TUI.readKey(true) // chamada bloqueante
                keyChannel.send(key)       // envia para o canal
            }
        }
    }

    fun stopReading() {
        scope.cancel()
        keyChannel.close()
    }

    fun getChannel(): Channel<KeyCode> = keyChannel
}
