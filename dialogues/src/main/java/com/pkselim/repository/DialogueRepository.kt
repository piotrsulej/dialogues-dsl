package com.pkselim.repository

import com.pkselim.data.DialogueLine
import com.pkselim.data.DialogueOption

interface DialogueRepository {

    fun getActiveDialogueLine(): DialogueLine?

    fun setActiveDialogueLine(dialogueLine: DialogueLine?)

    fun getSelectedDialogue(): DialogueOption?

    fun setSelectedDialogue(dialogue: DialogueOption?)
}