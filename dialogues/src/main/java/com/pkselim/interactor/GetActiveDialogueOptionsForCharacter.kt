package com.pkselim.interactor

import com.pkselim.data.Character
import com.pkselim.data.DialogueOption
import com.pkselim.data.DialogueReference
import com.pkselim.repository.DialogueRepository
import com.pkselim.repository.InMemoryDialogueRepository

class GetActiveDialogueOptionsForCharacter(
    private val dialogueRepository: DialogueRepository = InMemoryDialogueRepository.getInstance()
) : (Character) -> List<DialogueOption> {

    override fun invoke(character: Character): List<DialogueOption> =
        character.dialogueOptions
            .filterNot { option -> notPermanentOptionDiscussed(character, option) }

    private fun notPermanentOptionDiscussed(character: Character, option: DialogueOption): Boolean {
        val reference = DialogueReference.create(character, option)
        return dialogueRepository.isDiscussed(reference) && option.permanent.not()
    }
}