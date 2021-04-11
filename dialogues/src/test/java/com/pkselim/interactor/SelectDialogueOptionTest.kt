package com.pkselim.interactor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.pkselim.data.Character
import com.pkselim.data.DialogueOption
import com.pkselim.data.DialogueReference
import com.pkselim.repository.DialogueRepository
import org.junit.Test

class SelectDialogueOptionTest {

    private val dialogueRepository: DialogueRepository = mock()
    private val testSubject = SelectDialogueOption(dialogueRepository)

    @Test
    fun `Set as selected dialogue option`() {
        val character = Character(id = 0, "0", emptyList())
        val option = DialogueOption("1", emptyList(), id = 1)

        testSubject(option, character)

        verify(dialogueRepository).setSelectedDialogue(option)
    }

    @Test
    fun `Mark as discussed`() {
        val character = Character(id = 0, "0", emptyList())
        val option = DialogueOption("1", emptyList(), id = 1)
        val reference = DialogueReference.create(character, option)

        testSubject(option, character)

        verify(dialogueRepository).markAsDiscussed(reference)
    }
}