@file:Suppress("LocalVariableName")

package com.pkselim.repository.character.dialogues

import com.pkselim.dsl.dialogues
import com.pkselim.repository.character.Adam
import com.pkselim.repository.character.Tom

val Adam_dialogue_city = Adam dialogue 1
val Adam_dialogue_Tom = Adam dialogue 2

val AdamDialogues = dialogues(Adam) { You, Adam, Scene ->

    val Tom = Tom addTo Scene

    dialogue("How do you like this city?", Adam_dialogue_city) {

        You say "How do you like this city?"
        Adam says "I hate it, but at least I have good job here."
    }

    dialogue("What do you think about Tom?", Adam_dialogue_Tom) {

        You say "What do you think about Tom?"
        Adam says "He's so annoying..."
        Tom says "What did you say?"
    }
}