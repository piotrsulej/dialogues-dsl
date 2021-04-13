package com.pkselim.repository.character

import com.pkselim.data.Character
import com.pkselim.dsl.InstantiableCharacter
import com.pkselim.repository.character.dialogues.AdamDialogues

object Adam : InstantiableCharacter {

    override val id: Int = 2

    override fun instantiate(): Character =
        Character(
            name = "Adam",
            dialogueOptions = AdamDialogues,
            id = id
        )
}