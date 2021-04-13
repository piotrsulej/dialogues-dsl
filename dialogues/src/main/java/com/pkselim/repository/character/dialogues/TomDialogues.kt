package com.pkselim.repository.character.dialogues

import com.pkselim.dsl.dialogues
import com.pkselim.dsl.repeated
import com.pkselim.repository.character.Tom

val Tom_dialogue_how_are_you = Tom dialogue 1
val Tom_dialogue_Adam = Tom dialogue 2

val TomDialogues = dialogues(Tom) { You, Tom ->

    dialogue("How are you?", Tom_dialogue_how_are_you) {

        this can_be repeated

        You say "How are you?"
        Tom says "Fine, thank you. And you?"
        You say "Very good, thank you."
    }

    dialogue("Have you heard what Adam said about you?", Tom_dialogue_Adam) {

        this can_be_discussed_after Adam_dialogue_Tom

        You say "Have you heard what Adam said about you?"
        Tom says "Yes, it was very rude from him."
    }
}