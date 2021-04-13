package com.pkselim.repository

import com.pkselim.data.Character
import com.pkselim.data.DialogueLine
import com.pkselim.data.DialogueOption
import com.pkselim.data.DialogueReference

class CharactersRepository private constructor() {

    fun getCharacter(id: Int): Character? = getCharacters().find { it.id == id }

    fun getCharacters(): Set<Character> = CHARACTERS

    companion object {
        private val CHARACTERS = setOf(
            Character(
                name = "Tom",
                dialogueOptions = listOf(
                    DialogueOption(
                        description = "How are you?",
                        lines = listOf(
                            DialogueLine(message = "How are you?"),
                            DialogueLine(message = "Fine, thank you. And you?", npcId = 1),
                            DialogueLine(message = "Very good, thank you.")
                        ),
                        permanent = true,
                        id = 1
                    ),
                    DialogueOption(
                        description = "Have you heard what Adam said about you?",
                        lines = listOf(
                            DialogueLine(message = "Have you heard what Adam said about you?"),
                            DialogueLine(message = "Yes, it was very rude from him.", npcId = 1)
                        ),
                        id = 2,
                        allDialoguesDiscussed = setOf(DialogueReference(2, 2))
                    )
                ),
                id = 1
            ),
            Character(
                name = "Adam",
                dialogueOptions = listOf(
                    DialogueOption(
                        description = "How do you like this city?",
                        lines = listOf(
                            DialogueLine(message = "How do you like this city?"),
                            DialogueLine(
                                message = "I hate it, but at least I have good job here.",
                                npcId = 2
                            )
                        ),
                        id = 1
                    ),
                    DialogueOption(
                        description = "What do you think about Tom?",
                        lines = listOf(
                            DialogueLine(message = "What do you think about Tom?"),
                            DialogueLine(message = "He's so annoying...", npcId = 2),
                            DialogueLine(message = "What did you say?", npcId = 1)
                        ),
                        id = 2
                    ),
                ),
                id = 2
            )
        )

        @Synchronized
        @JvmStatic
        fun getInstance(): CharactersRepository = lazyInstance

        private val lazyInstance by lazy { CharactersRepository() }
    }
}