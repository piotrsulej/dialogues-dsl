package com.pkselim.interactor

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
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
        whenever(dialogueRepository.isDiscussed(discussedOption)).thenReturn(true)

        val result = testSubject(CHARACTER)

        assertThat(result).isEqualTo(expectedOptions)
    }

    @Test
    fun `Return discussed permanent options`() {
        val expectedOptions = DIALOGUE_OPTIONS.filter(DialogueOption::permanent)
        whenever(dialogueRepository.isDiscussed(any())).thenReturn(true)

        val result = testSubject(CHARACTER)

        assertThat(result).isEqualTo(expectedOptions)
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