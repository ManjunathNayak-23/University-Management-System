package com.developer.aurora.notesRoom

import androidx.lifecycle.LiveData

class NotesRepository(private val notesDao: NotesDao) {

    val readAllNotes: LiveData<List<Notes>> = notesDao.getAllNotes()

   suspend fun addNote(note: Notes) {
        notesDao.insertNote(note)
    }

   suspend fun deleteNote(note: Notes) {
        notesDao.deleteNote(note)
    }
   suspend fun updateNote(note: Notes) {
        notesDao.updateNote(note)
    }

    
}