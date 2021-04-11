package com.pkselim.data

data class DialogueOption constructor(
    val description: String,
    val lines: List<DialogueLine>,
    val permanent: Boolean = false,
    val id: Int
)