package com.pkselim.dsl

import com.pkselim.data.DialogueOption

@DslMarker
annotation class DialogueOptionsDsl

@DslMarker
annotation class NpcDsl

@DslMarker
annotation class HeroDsl

fun dialogues(
    character: InstantiableCharacter,
    specification: DialoguesBuilder.(HeroDialogueEventsBuilder, DialogueEventsBuilder) -> List<DialogueOption>
): List<DialogueOption> {
    val builder = DialoguesBuilder()
    return builder.specification(
        HeroDialogueEventsBuilder(dialoguesBuilder = builder),
        DialogueEventsBuilder(dialoguesBuilder = builder, npcId = character.id)
    )
}

fun dialogues(
    character: InstantiableCharacter,
    specification: DialoguesBuilder.(HeroDialogueEventsBuilder, DialogueEventsBuilder, DialoguesBuilder) -> List<DialogueOption>
): List<DialogueOption> {
    val builder = DialoguesBuilder()
    return builder.specification(
        HeroDialogueEventsBuilder(dialoguesBuilder = builder),
        DialogueEventsBuilder(dialoguesBuilder = builder, npcId = character.id),
        builder
    )
}