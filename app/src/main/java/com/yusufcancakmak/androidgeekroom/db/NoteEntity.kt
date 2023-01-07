package com.yusufcancakmak.androidgeekroom.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yusufcancakmak.androidgeekroom.utils.Constans.NOTE_TABLE

@Entity(tableName = NOTE_TABLE)
data class NoteEntity (
        @PrimaryKey(autoGenerate = true)
        val noteId: Int,
        @ColumnInfo(name = "note_title")
        val noteTitle:String,
        @ColumnInfo(name = "note_desc")
        val noteDesc:String


        )