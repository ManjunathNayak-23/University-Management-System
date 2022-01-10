package com.developer.aurora.notesRoom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    val readAllNotes: LiveData<List<Notes>>
    private val repository: NotesRepository

    init {
        val notesDao = NotesDatabase.getDatabase(application).notesDao()
        repository = NotesRepository(notesDao)
        readAllNotes = repository.readAllNotes
    }

    fun addNotes(note: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)

        }
    }

    fun deleteNote(note: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)

        }
    }

    fun updateNotes(note: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)

        }
    }

}