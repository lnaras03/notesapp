package com.example.notes.data

import android.net.Uri
import android.widget.ImageView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.fragments.allnotes.NotesAdapter

@Entity(tableName = "note_data_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,

    @ColumnInfo(name = "note_text")
    val noteText: String,

    @ColumnInfo(name = "note_image")
    val noteImage: String?,
)