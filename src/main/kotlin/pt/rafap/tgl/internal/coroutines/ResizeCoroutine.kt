package pt.rafap.tgl.internal.coroutines

import kotlinx.coroutines.delay
import pt.rafap.tgl.TGL

internal object ResizeCoroutine : TGLCoroutine {
    override var name: String = "ResizeCoroutine"
    override var function: suspend () -> Unit = {
        while (true) {
            TGL.resizeHandler()
            delay(100) // evita busy loop e permite reatividade
        }
    }
}