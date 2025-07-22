package pt.rafap.tui.datatype

data class KeyCode(val code: Int?) : Comparable<KeyCode> {
    override fun toString(): String {
        return code?.toChar()?.toString() ?: "null"
    }

    fun get(): String {
        return toString()
    }

    // implement range for KeyCode
    operator fun rangeTo(other: KeyCode): ClosedRange<KeyCode> {
        return object : ClosedRange<KeyCode> {
            override val start: KeyCode = this@KeyCode
            override val endInclusive: KeyCode = other
        }
    }

    override fun compareTo(other: KeyCode): Int {
        return when {
            this.code == other.code -> 0
            this.code == null -> -1
            other.code == null -> 1
            this.code < other.code -> -1
            else -> 1
        }
    }
}