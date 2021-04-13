package com.pkselim.data

data class Character(
    val id: Int,
    val name: String,
    val dialogueOptions: List<DialogueOption>
)