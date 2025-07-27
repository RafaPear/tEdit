package pt.rafap.tgl.internal.coroutines

import pt.rafap.tgl.coroutine.CoroutineManager

interface TGLCoroutine{
    val name: String
    val function: suspend () -> Unit

    fun create() {
        CoroutineManager.addCoroutine(name) {
            function()
        }
    }

    fun delete() {
        CoroutineManager.removeCoroutine(name)
    }

    fun start() {
        CoroutineManager.startCoroutine(name)
    }

    fun stop() {
        CoroutineManager.stopCoroutine(name)
    }

    fun restartCoroutine() {
        stop()
        delete()
        create()
        start()
    }
}