package com.pkselim.interactor

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.pkselim.data.Character
import com.pkselim.data.DialogueOption
import com.pkselim.data.DialogueReference
import com.pkselim.repository.DialogueRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetActiveDialogueOptionsForCharacterTest {

    private val dialogueRepository: DialogueRepository = mock()
    private val testSubject = GetActiveDialogueOptionsForCharacter(dialogueRepository)

    @Test
    fun `Return all options`() {
        val result = testSubject(CHARACTER)

        assertThat(result).isEqualTo(DIALOGUE_OPTIONS)
    }

    @Test
    fun `Don't return discussed options`() {
        val discussedOption = DialogueReference(0, 1)
        val expectedOptions = DIALOGUE_OPTIONS.drop(1)

        given(dialogueRepository.wasDiscussed(discussedOption)).willReturn(true)

        val result = testSubject(CHARACTER)

        assertThat(result).isEqualTo(expectedOptions)
    }

    @Test
    fun `Return discussed permanent options`() {
        val expectedOptions = DIALOGUE_OPTIONS.filter(DialogueOption::permanent)

        given(dialogueRepository.wasDiscussed(any())).willReturn(true)

        val result = testSubject(CHARACTER)

        assertThat(result).isEqualTo(expectedOptions)
    }

    @Test
    fun `Don't return options WHEN all of required dialogues has not been discussed`() {
        val requiredDialogue1 = DialogueReference(1, 2)
        val requiredDialogue2 = DialogueReference(4, 1)
        val options = listOf(
            DialogueOption(
                description = "0",
                lines = emptyList(),
                allDialoguesDiscussed = setOf(requiredDialogue1, requiredDialogue2),
                id = 0
            )
        )
        val testCharacter = CHARACTER.copy(dialogueOptions = options)

        given(dialogueRepository.wasDiscussed(requiredDialogue1)).willReturn(true)

        val result = testSubject(testCharacter)

        assertThat(result).isEmpty()
    }

    @Test
    fun `Return options WHEN all of required dialogues has been discussed`() {
        val requiredDialogue1 = DialogueReference(1, 2)
        val requiredDialogue2 = DialogueReference(4, 1)
        val options = listOf(
            DialogueOption(
                description = "0",
                lines = emptyList(),
                allDialoguesDiscussed = setOf(requiredDialogue1, requiredDialogue2),
                id = 0
            )
        )
        val testCharacter = CHARACTER.copy(dialogueOptions = options)

        given(dialogueRepository.wasDiscussed(requiredDialogue1)).willReturn(true)
        given(dialogueRepository.wasDiscussed(requiredDialogue2)).willReturn(true)

        val result = testSubject(testCharacter)

        assertThat(result).isEqualTo(options)
    }

    @Test
    fun `Don't return options WHEN none of required dialogues has not been discussed`() {
        val requiredDialogue1 = DialogueReference(1, 2)
        val requiredDialogue2 = DialogueReference(4, 1)
        val options = listOf(
            DialogueOption(
                description = "0",
                lines = emptyList(),
                anyDialogueDiscussed = setOf(requiredDialogue1, requiredDialogue2),
                id = 0
            )
        )
        val testCharacter = CHARACTER.copy(dialogueOptions = options)

        val result = testSubject(testCharacter)

        assertThat(result).isEmpty()
    }

    @Test
    fun `Return options WHEN any of required dialogues has been discussed`() {
        val requiredDialogue1 = DialogueReference(1, 2)
        val requiredDialogue2 = DialogueReference(4, 1)
        val options = listOf(
            DialogueOption(
                description = "0",
                lines = emptyList(),
                anyDialogueDiscussed = setOf(requiredDialogue1, requiredDialogue2),
                id = 0
            )
        )
        val testCharacter = CHARACTER.copy(dialogueOptions = options)

        given(dialogueRepository.wasDiscussed(requiredDialogue1)).willReturn(true)

        val result = testSubject(testCharacter)

        assertThat(result).isEqualTo(options)
    }

    @Test
    fun `Don't return options WHEN any of excluding dialogues has been discussed`() {
        val requiredDialogue1 = DialogueReference(1, 2)
        val requiredDialogue2 = DialogueReference(4, 1)
        val options = listOf(
            DialogueOption(
                description = "0",
                lines = emptyList(),
                noDialogueDiscussed = setOf(requiredDialogue1, requiredDialogue2),
                id = 0
            )
        )
        val testCharacter = CHARACTER.copy(dialogueOptions = options)

        given(dialogueRepository.wasDiscussed(requiredDialogue1)).willReturn(true)

        val result = testSubject(testCharacter)

        assertThat(result).isEmpty()
    }

    @Test
    fun `Return options WHEN none of excluding dialogues has not been discussed`() {
        val requiredDialogue1 = DialogueReference(1, 2)
        val requiredDialogue2 = DialogueReference(4, 1)
        val options = listOf(
            DialogueOption(
                description = "0",
                lines = emptyList(),
                noDialogueDiscussed = setOf(requiredDialogue1, requiredDialogue2),
                id = 0
            )
        )
        val testCharacter = CHARACTER.copy(dialogueOptions = options)

        val result = testSubject(testCharacter)

        assertThat(result).isEqualTo(options)
    }

    @Test
    fun `Return options WHEN any of excluding dialogues has not been discussed`() {
        val requiredDialogue1 = DialogueReference(1, 2)
        val requiredDialogue2 = DialogueReference(4, 1)
        val options = listOf(
            DialogueOption(
                description = "0",
                lines = emptyList(),
                anyDialogueNotDiscussed = setOf(requiredDialogue1, requiredDialogue2),
                id = 0
            )
        )
        val testCharacter = CHARACTER.copy(dialogueOptions = options)

        given(dialogueRepository.wasDiscussed(requiredDialogue1)).willReturn(true)

        val result = testSubject(testCharacter)

        assertThat(result).isEqualTo(options)
    }

    @Test
    fun `Don't return options WHEN all of excluding dialogues has been discussed`() {
        val requiredDialogue1 = DialogueReference(1, 2)
        val requiredDialogue2 = DialogueReference(4, 1)
        val options = listOf(
            DialogueOption(
                description = "0",
                lines = emptyList(),
                anyDialogueNotDiscussed = setOf(requiredDialogue1, requiredDialogue2),
                id = 0
            )
        )
        val testCharacter = CHARACTER.copy(dialogueOptions = options)

        given(dialogueRepository.wasDiscussed(requiredDialogue1)).willReturn(true)
        given(dialogueRepository.wasDiscussed(requiredDialogue2)).willReturn(true)

        val result = testSubject(testCharacter)

        assertThat(result).isEmpty()
    }

    companion object {
        private val DIALOGUE_OPTIONS = (1..10).map { index ->
            DialogueOption(
                description = index.toString(), lines = emptyList(),
                permanent = index.rem(2) == 0,
                id = index
            )
        }
        private val CHARACTER = Character(
            id = 0,
            name = "0",
            dialogueOptions = DIALOGUE_OPTIONS
        )
    }
}