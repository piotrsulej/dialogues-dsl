package com.pkselim.repository.character

import com.pkselim.data.Character
import com.pkselim.dsl.InstantiableCharacter
import com.pkselim.repository.character.dialogues.TomDialogues

object Tom : InstantiableCharacter {

    override val id: Int = 1

    override fun instantiate(): Character =
        Character(
            name = "Tom",
            dialogueOptions = TomDialogues,
            id = id
        )
}