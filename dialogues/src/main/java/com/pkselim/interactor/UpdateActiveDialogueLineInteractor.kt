package com.pkselim.interactor

import com.pkselim.data.DialogueLine
import com.pkselim.repository.DialogueRepository
import com.pkselim.repository.InMemoryDialogueRepository

class UpdateActiveDialogueLineInteractor(
    private val dialogueRepository: DialogueRepository = InMemoryDialogueRepository.getInstance()
) {

    fun updateActiveDialogueLine() {
        val lines = dialogueLines()
        val dialogueEvent = dialogueRepository.getActiveDialogueLine()
        val nextDialogueIndex = lines.indexOf(dialogueEvent).inc()
        val nextLine = lines.getOrNull(nextDialogueIndex)
        if (nextLine != null) {
            updateActiveDialogueLine(nextLine)
        } else {
            resetActiveDialogueLine()
        }
    }

    private fun dialogueLines(): List<DialogueLine> {
        val dialogue = dialogueRepository.getSelectedDialogue()
        return dialogue?.lines?.toList() ?: emptyList()
    }

    private fun updateActiveDialogueLine(line: DialogueLine) {
        dialogueRepository.setActiveDialogueLine(line)
    }

    private fun resetActiveDialogueLine() {
        dialogueRepository.setActiveDialogueLine(null)
    }
}