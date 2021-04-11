package com.pkselim.interactor

import com.pkselim.data.Character
import com.pkselim.data.DialogueOption
import com.pkselim.data.DialogueReference
import com.pkselim.repository.DialogueRepository
import com.pkselim.repository.InMemoryDialogueRepository

class SelectDialogueOption(
    private val dialogueRepository: DialogueRepository = InMemoryDialogueRepository.getInstance()
) : (DialogueOption?, Character) -> Unit {

    override fun invoke(dialogueOption: DialogueOption?, character: Character) {
        dialogueRepository.setSelectedDialogue(dialogueOption)
        if (dialogueOption != null) {
            val reference = DialogueReference.create(character, dialogueOption)
            dialogueRepository.markAsDiscussed(reference)
        }
    }
}