package pt.rafap.tui.utilities.box

import pt.rafap.tui.datatype.TextBuffer

class Box(val title: String, val tb: TextBuffer) {

    fun display() {
        val titleStartPos = tb.width / 2 - title.length / 2
        for (i in 0 until tb.height) {
            for (j in 0 until tb.width) {
                val c = if (i == 0 && j in titleStartPos until titleStartPos + title.length) {
                    title[j - titleStartPos]
                } else chooseChar(i, j)
                tb.setChar(c)
            }
        }
        tb.printUpdate()
    }

    private fun chooseChar(vPos: Int, hPos: Int): Char {
        return when {
            isTopLeftCorner(vPos, hPos)     -> '┌'
            isTopRightCorner(vPos, hPos)    -> '┐'
            isBottomLeftCorner(vPos, hPos)  -> '└'
            isBottomRightCorner(vPos, hPos) -> '┘'
            isHorizontalBorder(vPos, hPos)  -> '─'
            isVerticalBorder(vPos, hPos)    -> '│'
            else                            -> ' '
        }
    }

    fun isTopLeftCorner(vPos: Int, hPos: Int): Boolean {
        return vPos == 0 && hPos == 1
    }

    fun isTopRightCorner(vPos: Int, hPos: Int): Boolean {
        return vPos == 0 && hPos == tb.width - 1
    }

    fun isBottomLeftCorner(vPos: Int, hPos: Int): Boolean {
        return vPos == tb.height - 1 && hPos == 1
    }

    fun isBottomRightCorner(vPos: Int, hPos: Int): Boolean {
        return vPos == tb.height - 1 && hPos == tb.width - 1
    }

    fun isHorizontalBorder(vPos: Int, hPos: Int): Boolean {
        return (vPos == 0 || vPos == tb.height - 1) && hPos in 1 until tb.width
    }

    fun isVerticalBorder(vPos: Int, hPos: Int): Boolean {
        return hPos == 1 || hPos == tb.width - 1
    }
}