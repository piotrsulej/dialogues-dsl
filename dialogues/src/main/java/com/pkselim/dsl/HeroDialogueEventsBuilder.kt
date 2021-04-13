@file:Suppress("FunctionName")

package com.pkselim.dsl

data class HeroDialogueEventsBuilder(private val dialoguesBuilder: DialoguesBuilder) {

    private val dialogueEventsBuilder = DialogueEventsBuilder(dialoguesBuilder, npcId = null)

    @HeroDsl
    infix fun say(message: String) = dialogueEventsBuilder.says(message)
}