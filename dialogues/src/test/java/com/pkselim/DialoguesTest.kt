package com.pkselim

import com.pkselim.interactor.GetActiveDialogueOptionsForCharacter
import com.pkselim.interactor.SelectDialogueOption
import com.pkselim.interactor.UpdateActiveDialogueLine
import com.pkselim.repository.CharactersRepository
import com.pkselim.repository.DialogueRepository
import com.pkselim.repository.InMemoryDialogueRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class DialoguesTest {

    private lateinit var charactersRepository: CharactersRepository
    private lateinit var dialogueRepository: DialogueRepository
    private lateinit var updateActiveDialogueLine: UpdateActiveDialogueLine
    private lateinit var selectDialogueOption: SelectDialogueOption
    private lateinit var getActiveDialogueOptionsForCharacter: GetActiveDialogueOptionsForCharacter

    @Before
    fun setUp() {
        charactersRepository = CharactersRepository.getInstance()
        dialogueRepository = InMemoryDialogueRepository()
        updateActiveDialogueLine = UpdateActiveDialogueLine(dialogueRepository)
        selectDialogueOption = SelectDialogueOption(dialogueRepository)
        getActiveDialogueOptionsForCharacter =
            GetActiveDialogueOptionsForCharacter(dialogueRepository)
    }

    @Test
    fun `Get all dialogues`() {
        val character = charactersRepository.getCharacter(2)!!

        val result = getActiveDialogueOptionsForCharacter(character)

        assertThat(result).hasSize(2)
    }

    @Test
    fun `Get all dialogues without already discussed one`() {
        val character = charactersRepository.getCharacter(2)!!
        val option = character.dialogueOptions.find { it.id == 1 }!!

        selectDialogueOption(option, character)

        val result = getActiveDialogueOptionsForCharacter(character)

        assertThat(result).hasSize(1)
    }

    @Test
    fun `Get all dialogues without one that has unsatisfied dependencies`() {
        val character = charactersRepository.getCharacter(1)!!

        val result = getActiveDialogueOptionsForCharacter(character)

        assertThat(result).hasSize(1)
    }

    @Test
    fun `Get all dialogues with satisfied dependencies`() {
        val dependencyCharacter = charactersRepository.getCharacter(2)!!
        val dependencyOption = dependencyCharacter.dialogueOptions.find { it.id == 2 }!!
        val character = charactersRepository.getCharacter(1)!!

        selectDialogueOption(dependencyOption, dependencyCharacter)

        val result = getActiveDialogueOptionsForCharacter(character)

        assertThat(result).hasSize(2)
    }

    @Test
    fun `Get discussed permanent dialogue`() {
        val character = charactersRepository.getCharacter(1)!!
        val option = character.dialogueOptions.find { it.id == 1 }!!

        selectDialogueOption(option, character)

        val result = getActiveDialogueOptionsForCharacter(character)

        assertThat(result).hasSize(1)
        assertThat(result.firstOrNull { it.id == 1 }).isNotNull
    }
}