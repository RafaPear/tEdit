package pt.rafap.tEdit.typeExt

import pt.rafap.tEdit.tools.ESC

fun String.makeESCCode() = "$ESC$this"