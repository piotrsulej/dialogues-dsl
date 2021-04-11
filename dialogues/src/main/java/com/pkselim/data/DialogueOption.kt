package com.pkselim.data

data class DialogueOption constructor(
    val description: String,
    val lines: List<DialogueLine>,
    val isProactive: Boolean = false,
    val id: Int
)