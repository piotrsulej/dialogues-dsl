@file:Suppress("FunctionName", "PropertyName", "ClassName", "UNUSED_PARAMETER")

package com.pkselim.dsl

import com.pkselim.data.DialogueLine
import com.pkselim.data.DialogueOption
import com.pkselim.data.DialogueReference

object repeated

class DialogueOptionBuilder(var dialogue: DialogueOption) {

    fun addDialogueLine(message: String, npcId: Int?) = apply {
        dialogue = dialogue.copy(
            lines = dialogue.lines.plus(
                DialogueLine(message, npcId)
            )
        )
    }.dialogue

    @DialogueOptionsDsl
    infix fun has_to_be_discussed_before(dialogueReference: DialogueReference) = apply {
        dialogue = dialogue.copy(
            noDialogueDiscussed = dialogue.noDialogueDiscussed + dialogueReference
        )
    }.dialogue

    @DialogueOptionsDsl
    infix fun can_be_discussed_after_all(dialoguesReferences: List<DialogueReference>) = apply {
        dialogue = dialogue.copy(
            allDialoguesDiscussed = dialoguesReferences
        )
    }.dialogue

    @DialogueOptionsDsl
    infix fun can_be_discussed_after_any(dialoguesReferences: List<DialogueReference>) = apply {
        dialogue = dialogue.copy(
            anyDialogueDiscussed = dialoguesReferences
        )
    }.dialogue

    @DialogueOptionsDsl
    infix fun has_to_be_discussed_before_all(dialoguesReferences: List<DialogueReference>) = apply {
        dialogue = dialogue.copy(
            anyDialogueNotDiscussed = dialoguesReferences
        )
    }.dialogue

    @DialogueOptionsDsl
    infix fun can_be_discussed_after(dialogueReference: DialogueReference) = apply {
        dialogue = dialogue.copy(
            allDialoguesDiscussed = dialogue.allDialoguesDiscussed + dialogueReference
        )
    }.dialogue

    @DialogueOptionsDsl
    infix fun can_be(repeated: repeated) = apply {
        dialogue = dialogue.copy(permanent = true)
    }.dialogue
}