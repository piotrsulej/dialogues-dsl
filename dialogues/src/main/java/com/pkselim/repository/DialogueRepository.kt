package com.pkselim.repository

import com.pkselim.data.DialogueLine
import com.pkselim.data.DialogueOption
import com.pkselim.data.DialogueReference

interface DialogueRepository {

    fun getActiveDialogueLine(): DialogueLine?

    fun setActiveDialogueLine(dialogueLine: DialogueLine?)

    fun getSelectedDialogue(): DialogueOption?

    fun setSelectedDialogue(dialogue: DialogueOption?)

    fun markAsDiscussed(dialogue: DialogueReference)

    fun wasDiscussed(dialogue: DialogueReference): Boolean
}