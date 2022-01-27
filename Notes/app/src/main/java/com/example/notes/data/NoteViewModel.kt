package com.example.notes.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val notesDao: NoteDao = NoteDatabase.getDatabase(application).noteDao

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    fun insert(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            notesDao.insertNote(note)
        }
    }

    fun delete(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            notesDao.deleteNote(id)
        }
    }

    fun update(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            notesDao.updateNote(note)
        }
    }

}