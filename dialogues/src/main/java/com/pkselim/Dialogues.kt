package com.pkselim

import com.pkselim.data.Character
import com.pkselim.data.DialogueOption
import com.pkselim.interactor.GetActiveDialogueOptionsForCharacter
import com.pkselim.interactor.SelectDialogueOption
import com.pkselim.interactor.UpdateActiveDialogueLine
import com.pkselim.repository.CharactersRepository
import com.pkselim.repository.DialogueRepository
import com.pkselim.repository.InMemoryDialogueRepository

val charactersRepository = CharactersRepository.getInstance()
val dialogueRepository: DialogueRepository = InMemoryDialogueRepository.getInstance()
val updateActiveDialogueLine = UpdateActiveDialogueLine()
val selectDialogueOption = SelectDialogueOption()
val getActiveDialogueOptionsForCharacter = GetActiveDialogueOptionsForCharacter()

fun main() {
    processInput()
}

private fun processInput() {
    println("Type name of character that you want to talk with. Type something else if you don't want to talk.")
    charactersRepository.getCharacters().forEach { character ->
        println(character.name)
    }
    processInput(readLine().orEmpty())
}

private fun processInput(input: String) {
    val selectedNpc = charactersRepository.getCharacters().firstOrNull { character ->
        character.name.equals(input, ignoreCase = true)
    }
    if (selectedNpc != null) {
        processNpcSelection(selectedNpc)
        println()
        processInput()
    } else {
        println("Goodbye then.")
    }
}

private fun processNpcSelection(character: Character) {
    val options = getActiveDialogueOptionsForCharacter(character)
    if (options.isNotEmpty()) {
        printDialogueOptions(options)
        processDialogueOptionSelection(options, character)
    } else {
        println("${character.name} doesn't want to talk with you.")
    }
}

private fun printDialogueOptions(options: List<DialogueOption>) {
    println("Type number of dialogue option that you want to select.")
    options.forEachIndexed { index, dialogueOption ->
        println("${index.inc()}. ${dialogueOption.description}")
    }
}

private fun processDialogueOptionSelection(options: List<DialogueOption>, character: Character) {
    val dialogueOption = readLine()?.toIntOrNull()?.dec()?.let { index -> options[index] }
    selectDialogueOption(dialogueOption, character)
    processDialogueLine()
}

private fun processDialogueLine() {
    updateActiveDialogueLine()
    dialogueRepository.getActiveDialogueLine()?.let { line ->
        val prefix = line.npcId
            ?.let { charactersRepository.getCharacter(it)?.name }
            ?: "You".takeIf { line.isNarration.not() }
        println("${prefix}: ${line.message}")
    }?.also {
        processDialogueLine()
    }
}