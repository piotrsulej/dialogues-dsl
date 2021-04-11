package com.pkselim.interactor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pkselim.data.DialogueLine
import com.pkselim.data.DialogueOption
import com.pkselim.repository.DialogueRepository
import org.junit.Test

class UpdateActiveDialogueLineTest {

    private val dialogueRepository: DialogueRepository = mock()
    private val testSubject = UpdateActiveDialogueLine(dialogueRepository)

    @Test
    fun `No active line`() {
        whenever(dialogueRepository.getSelectedDialogue()).thenReturn(null)

        testSubject()

        verify(dialogueRepository).setActiveDialogueLine(null)
    }

    @Test
    fun `First dialogue line of selected option`() {
        val firstDialogueLine = DialogueLine(message = "1")
        val selectedOption = DialogueOption(
            description = "123",
            lines = listOf(
                firstDialogueLine,
                DialogueLine(message = "2"),
                DialogueLine(message = "3")
            ),
            id = 123
        )

        whenever(dialogueRepository.getSelectedDialogue()).thenReturn(selectedOption)

        testSubject()

        verify(dialogueRepository).setActiveDialogueLine(firstDialogueLine)
    }

    @Test
    fun `Next dialogue line of selected option`() {
        val currentActiveDialogueLine = DialogueLine(message = "2")
        val nextDialogueLine = DialogueLine(message = "3")
        val selectedOption = DialogueOption(
            description = "1234",
            lines = listOf(
                DialogueLine(message = "1"),
                currentActiveDialogueLine,
                nextDialogueLine,
                DialogueLine(message = "4")
            ),
            id = 1234
        )

        whenever(dialogueRepository.getActiveDialogueLine()).thenReturn(currentActiveDialogueLine)
        whenever(dialogueRepository.getSelectedDialogue()).thenReturn(selectedOption)

        testSubject()

        verify(dialogueRepository).setActiveDialogueLine(nextDialogueLine)
    }
}