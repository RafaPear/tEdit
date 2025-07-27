package pt.rafap.tgl.internal.coroutines

import pt.rafap.tgl.TGL.inputHandler
import pt.rafap.tgl.internal.InputReader

internal object KeyInputCoroutine: TGLCoroutine {
    override var name: String = "KeyInputThread"
    override var function: suspend () -> Unit = {
        InputReader.startReading()
        val channel = InputReader.getChannel()
        while (true) {
            val key = channel.receive()
            inputHandler(key)
        }
    }

    override fun stop() {
        super.stop()
        InputReader.stopReading()
    }
}