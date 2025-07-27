package pt.rafap.tgl.coroutine

import kotlinx.coroutines.*
import pt.rafap.tgl.tui.logger.Logger
import pt.rafap.tgl.tui.logger.Severity
import kotlin.collections.iterator

object CoroutineManager {

    private val coroutineJobs = mutableMapOf<String, Job>()
    private val coroutineScopes = mutableMapOf<String, CoroutineScope>()

    fun addCoroutine(name: String, block: suspend CoroutineScope.() -> Unit) {
        if (coroutineJobs.containsKey(name)) {
            Logger.log("Coroutine '$name' já existe.", Severity.WARNING)
            return
        }

        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val job = scope.launch(start = CoroutineStart.LAZY) {
            block()
        }

        coroutineScopes[name] = scope
        coroutineJobs[name] = job
    }

    fun getCoroutine(name: String): Job? {
        return coroutineJobs[name]
    }

    fun removeCoroutine(name: String) {
        coroutineJobs[name]?.cancel()
        coroutineJobs.remove(name)
        coroutineScopes.remove(name)
    }

    fun startCoroutine(name: String) {
        val job = coroutineJobs[name]
        if (job == null) {
            Logger.log("Coroutine '$name' não existe.", Severity.ERROR)
            return
        }
        if (job.isActive) {
            Logger.log("Coroutine '$name' já está a correr.", Severity.INFO)
            return
        }
        job.start()
    }

    fun stopCoroutine(name: String) {
        coroutineJobs[name]?.cancel()
    }

    fun isCoroutineRunning(name: String): Boolean {
        return coroutineJobs[name]?.isActive ?: false
    }

    fun stopAllCoroutines() {
        coroutineJobs.values.forEach { it.cancel() }
        coroutineJobs.clear()
        coroutineScopes.clear()
    }

    fun startAllCoroutines() {
        coroutineJobs.forEach { (name, job) ->
            if (!job.isActive && job.isCancelled.not()) {
                job.start()
            } else {
                Logger.log("Coroutine '$name' já está a correr.", Severity.INFO)
            }
        }
    }

    fun clearCoroutines() {
        coroutineJobs.clear()
        coroutineScopes.clear()
    }

    fun getAllCoroutines(): Map<String, Job> {
        return coroutineJobs.toMap()
    }

    fun isEmpty(): Boolean {
        return coroutineJobs.isEmpty()
    }

    fun size(): Int {
        return coroutineJobs.size
    }

    fun logCoroutines() {
        if (coroutineJobs.isEmpty()) {
            Logger.log("Sem coroutines ativas.", Severity.INFO)
        } else {
            Logger.log("Estado das coroutines:", Severity.INFO)
            for ((name, job) in coroutineJobs) {
                val status = when {
                    job.isCancelled -> "Cancelada"
                    job.isActive -> "A correr"
                    job.isCompleted -> "Concluída"
                    else -> "Preparada"
                }
                Logger.log("- $name: $status", Severity.INFO)
            }
        }
    }

    fun stopAndClear() {
        stopAllCoroutines()
        clearCoroutines()
    }
}
