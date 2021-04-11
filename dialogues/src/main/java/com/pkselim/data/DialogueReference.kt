package com.pkselim.data

data class DialogueReference constructor(val characterId: Int, val dialogueOptionId: Int) {

    companion object {

        fun create(character: Character, dialogueOption: DialogueOption): DialogueReference =
            DialogueReference(character.id, dialogueOption.id)
    }
}
