@file:Suppress("SpellCheckingInspection", "FunctionName")

package com.pkselim.dsl

import com.pkselim.data.DialogueOption
import com.pkselim.data.DialogueReference

class DialoguesBuilder {

    var dialogueBuilder: DialogueOptionBuilder? = null
    private val dialogues: MutableList<DialogueOption> = mutableListOf()

    fun dialogue(
        description: String,
        dialogueReference: DialogueReference,
        specification: (DialogueOptionBuilder.() -> DialogueOption?) = { dialogue }
    ): MutableList<DialogueOption> = apply {
        val dialogue = DialogueOption(
            description = description,
            id = dialogueReference.dialogueOptionId,
            lines = emptyList()
        )
        dialogueBuilder = DialogueOptionBuilder(dialogue)
        val dialogueFromSpecification = dialogueBuilder?.specification() ?: return@apply
        dialogues.add(dialogueFromSpecification)
    }.dialogues
}