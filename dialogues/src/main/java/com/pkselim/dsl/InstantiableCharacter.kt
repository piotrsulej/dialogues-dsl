package com.pkselim.dsl

import com.pkselim.data.Character
import com.pkselim.data.DialogueReference

interface InstantiableCharacter {

    val id: Int

    fun instantiate(): Character

    infix fun addTo(builder: DialoguesBuilder) =
        DialogueEventsBuilder(builder, id)

    infix fun dialogue(dialogueId: Int) = DialogueReference(id, dialogueId)
}