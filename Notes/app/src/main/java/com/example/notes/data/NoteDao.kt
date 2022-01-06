package com.example.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note:Note)

    @Update
    suspend fun updateNote(note:Note)

    @Query("DELETE FROM note_data_table WHERE noteId = :id")
    suspend fun deleteNote(id: Int)

    @Query("SELECT * FROM note_data_table ")
    fun getAllNotes() : LiveData<List<Note>>


}