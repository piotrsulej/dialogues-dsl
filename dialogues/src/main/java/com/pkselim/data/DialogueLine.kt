package com.pkselim.data

data class DialogueLine constructor(
    val message: String,
    val npcId: Int? = null,
    val isNarration: Boolean = false
)