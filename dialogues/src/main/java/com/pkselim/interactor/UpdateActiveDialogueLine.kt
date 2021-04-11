package com.pkselim.interactor

import com.pkselim.data.DialogueLine
import com.pkselim.repository.DialogueRepository
import com.pkselim.repository.InMemoryDialogueRepository

class UpdateActiveDialogueLine(
    private val dialogueRepository: DialogueRepository = InMemoryDialogueRepository.getInstance()
) : () -> Unit {

    override fun invoke() {
        val lines = dialogueLines()
        val dialogueEvent = dialogueRepository.getActiveDialogueLine()
        val nextDialogueIndex = lines.indexOf(dialogueEvent).inc()
        val nextLine = lines.getOrNull(nextDialogueIndex)
        if (nextLine != null) {
            setActiveDialogueLine(nextLine)
        } else {
            resetActiveDialogueLine()
        }
    }

    private fun dialogueLines(): List<DialogueLine> {
        val dialogue = dialogueRepository.getSelectedDialogue()
        return dialogue?.lines?.toList() ?: emptyList()
    }

    private fun setActiveDialogueLine(line: DialogueLine) {
        dialogueRepository.setActiveDialogueLine(line)
    }

    private fun resetActiveDialogueLine() {
        dialogueRepository.setActiveDialogueLine(null)
    }
}