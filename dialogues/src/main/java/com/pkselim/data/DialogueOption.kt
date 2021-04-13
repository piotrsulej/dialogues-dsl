package com.pkselim.data

data class DialogueOption constructor(
    val description: String,
    val lines: List<DialogueLine>,
    val allDialoguesDiscussed: Collection<DialogueReference> = emptySet(),
    val anyDialogueDiscussed: Collection<DialogueReference> = emptySet(),
    val noDialogueDiscussed: Collection<DialogueReference> = emptySet(),
    val anyDialogueNotDiscussed: Collection<DialogueReference> = emptySet(),
    val permanent: Boolean = false,
    val id: Int
)