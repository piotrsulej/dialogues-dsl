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
            .asSequence()
            .filterNot { option -> notPermanentOptionDiscussed(character, option) }
            .filter(this::anyDiscussedDialogues)
            .filter(this::allDiscussedDialogues)
            .filter(this::anyNotDiscussedDialogues)
            .filter(this::allNotDiscussedDialogues)
            .toList()

    private fun notPermanentOptionDiscussed(character: Character, option: DialogueOption): Boolean {
        val reference = DialogueReference.create(character, option)
        return dialogueRepository.wasDiscussed(reference) && option.permanent.not()
    }

    private fun anyNotDiscussedDialogues(dialogue: DialogueOption): Boolean =
        dialogue.anyDialogueNotDiscussed.isEmpty() || dialogue.anyDialogueNotDiscussed.any { requiredDialogue ->
            dialogueRepository.wasDiscussed(requiredDialogue).not()
        }

    private fun anyDiscussedDialogues(dialogue: DialogueOption): Boolean =
        dialogue.anyDialogueDiscussed.isEmpty() || dialogue.anyDialogueDiscussed.any { requiredDialogue ->
            dialogueRepository.wasDiscussed(requiredDialogue)
        }

    private fun allDiscussedDialogues(dialogue: DialogueOption): Boolean =
        dialogue.allDialoguesDiscussed.all { requiredDialogue ->
            dialogueRepository.wasDiscussed(requiredDialogue)
        }

    private fun allNotDiscussedDialogues(dialogue: DialogueOption): Boolean =
        dialogue.noDialogueDiscussed.all { requiredDialogue ->
            dialogueRepository.wasDiscussed(requiredDialogue).not()
        }
}