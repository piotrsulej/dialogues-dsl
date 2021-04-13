@file:Suppress("FunctionName")

package com.pkselim.dsl

data class DialogueEventsBuilder(
    val dialoguesBuilder: DialoguesBuilder,
    val npcId: Int?
) {

    @NpcDsl
    infix fun says(message: String) =
        dialoguesBuilder.dialogueBuilder?.addDialogueLine(message, npcId)
}