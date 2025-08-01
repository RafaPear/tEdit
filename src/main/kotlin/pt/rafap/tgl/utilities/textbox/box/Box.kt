package pt.rafap.tgl.utilities.textbox.box

import pt.rafap.tgl.utilities.textbox.box.textbuffer.TextBuffer

interface Box {
    val title: String
    val width: Int
    val height: Int
    val tb: TextBuffer
    val style: BoxStyle

    fun display() {
        tb.printUpdate()
    }

    fun rePrint() {
        tb.printUpdate(true)
    }

    fun initialize() {
        val titleStartPos = tb.width / 2 - title.length / 2
        for (i in 0 until tb.height) {
            for (j in 0 until tb.width) {
                val c = if (i == 0 && j in titleStartPos until titleStartPos + title.length) {
                    title[j - titleStartPos]
                } else chooseChar(i, j)
                tb.setChar(c)
            }
        }
    }

    fun chooseChar(vPos: Int, hPos: Int): Char {
        return when {
            !style.drawLines -> style.fillChar
            isTopLeftCorner(vPos, hPos)     -> style.tLChar
            isTopRightCorner(vPos, hPos)    -> style.tRChar
            isBottomLeftCorner(vPos, hPos)  -> style.bLChar
            isBottomRightCorner(vPos, hPos) -> style.bRChar
            isHorizontalBorder(vPos, hPos)  -> style.hBChar
            isVerticalBorder(vPos, hPos)    -> style.vBChar
            else                            -> style.fillChar
        }
    }

    fun isTopLeftCorner(vPos: Int, hPos: Int): Boolean {
        return vPos == 0 && hPos == 1
    }

    fun isTopRightCorner(vPos: Int, hPos: Int): Boolean {
        return vPos == 0 && hPos == tb.width - 2
    }

    fun isBottomLeftCorner(vPos: Int, hPos: Int): Boolean {
        return vPos == tb.height - 1 && hPos == 1
    }

    fun isBottomRightCorner(vPos: Int, hPos: Int): Boolean {
        return vPos == tb.height - 1 && hPos == tb.width - 2
    }

    fun isHorizontalBorder(vPos: Int, hPos: Int): Boolean {
        return (vPos == 0 || vPos == tb.height - 1) && hPos in 1 until tb.width - 1
    }

    fun isVerticalBorder(vPos: Int, hPos: Int): Boolean {
        return hPos == 1 || hPos == tb.width - 2
    }
}