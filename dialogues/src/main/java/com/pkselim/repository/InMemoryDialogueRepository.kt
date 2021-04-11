package com.pkselim.repository

import com.pkselim.data.DialogueLine
import com.pkselim.data.DialogueOption
import com.pkselim.data.DialogueReference

class InMemoryDialogueRepository private constructor() : DialogueRepository {

    private var discussedDialogues: MutableSet<DialogueReference> = mutableSetOf()
    private var activeDialogueLine: DialogueLine? = null
    private var selectedDialogue: DialogueOption? = null

    override fun getActiveDialogueLine(): DialogueLine? = activeDialogueLine

    override fun setActiveDialogueLine(dialogueLine: DialogueLine?) {
        this.activeDialogueLine = dialogueLine
    }

    override fun getSelectedDialogue(): DialogueOption? = selectedDialogue

    override fun setSelectedDialogue(dialogue: DialogueOption?) {
        selectedDialogue = dialogue
    }

    override fun markAsDiscussed(dialogue: DialogueReference) {
        discussedDialogues.add(dialogue)
    }

    override fun isDiscussed(dialogue: DialogueReference): Boolean =
        discussedDialogues.contains(dialogue)

    companion object {

        @Synchronized
        @JvmStatic
        fun getInstance(): InMemoryDialogueRepository = lazyInstance

        private val lazyInstance by lazy { InMemoryDialogueRepository() }
    }
}