package com.yusufcancakmak.androidgeekroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.material.snackbar.Snackbar
import com.yusufcancakmak.androidgeekroom.databinding.ActivityAddNoteBinding
import com.yusufcancakmak.androidgeekroom.db.NoteDatabase
import com.yusufcancakmak.androidgeekroom.db.NoteEntity
import com.yusufcancakmak.androidgeekroom.utils.Constans.NOTE_DATABASE

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private val noteDB: NoteDatabase by lazy {
        Room.databaseBuilder(this, NoteDatabase::class.java, NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private lateinit var noteEntity: NoteEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc = edtDesc.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()) {
                    noteEntity = NoteEntity(0, title, desc)
                    noteDB.dao().insertNote(noteEntity)
                    finish()

                } else {
                    Snackbar.make(
                        it,
                        "Title and Descriptions connat be empty",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}