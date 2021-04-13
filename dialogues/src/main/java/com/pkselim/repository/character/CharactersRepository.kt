package com.pkselim.repository.character

import com.pkselim.data.Character

class CharactersRepository private constructor() {

    fun getCharacter(id: Int): Character? = getCharacters().find { it.id == id }

    fun getCharacters(): Set<Character> = CHARACTERS

    companion object {

        private val CHARACTERS = setOf(Tom.instantiate(), Adam.instantiate())

        @Synchronized
        @JvmStatic
        fun getInstance(): CharactersRepository = lazyInstance

        private val lazyInstance by lazy { CharactersRepository() }
    }
}