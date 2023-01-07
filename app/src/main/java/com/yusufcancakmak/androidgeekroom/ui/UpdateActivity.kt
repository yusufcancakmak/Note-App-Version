package com.yusufcancakmak.androidgeekroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.yusufcancakmak.androidgeekroom.R
import com.yusufcancakmak.androidgeekroom.databinding.ActivityUpdateBinding
import com.yusufcancakmak.androidgeekroom.db.NoteDatabase
import com.yusufcancakmak.androidgeekroom.db.NoteEntity
import com.yusufcancakmak.androidgeekroom.utils.Constans.BUNDLE_NOTE_ID
import com.yusufcancakmak.androidgeekroom.utils.Constans.NOTE_DATABASE

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private val noteDB :NoteDatabase by lazy {
        Room.databaseBuilder(this,NoteDatabase::class.java,NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    //Sectiğimiz verileri bilgileriyle birlikte görmek için
    private lateinit var noteEntity: NoteEntity
    private var noteId=0
    private var defaultTitle=""
    private var defaultDesc=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            noteId = it.getInt(BUNDLE_NOTE_ID)

        }
        binding.apply {
            defaultTitle=noteDB.dao().getNote(noteId).noteTitle
            defaultDesc=noteDB.dao().getNote(noteId).noteDesc

            edtTitle.setText(defaultTitle)
            edtDesc.setText(defaultDesc)

                    //Silme işlemi için
            btnDelete.setOnClickListener {
                noteEntity=NoteEntity(noteId,defaultTitle,defaultDesc)
                noteDB.dao().deleteNote(noteEntity)
                finish()
            }
            btnSave.setOnClickListener {
                val title=edtTitle.text.toString()
                val desc=edtDesc.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()) {
                    noteEntity = NoteEntity(noteId, title, desc)
                    noteDB.dao().updateNote(noteEntity)
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