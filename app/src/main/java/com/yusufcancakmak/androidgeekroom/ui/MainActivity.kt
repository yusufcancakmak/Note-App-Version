package com.yusufcancakmak.androidgeekroom.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.yusufcancakmak.androidgeekroom.adapter.NoteAdapter
import com.yusufcancakmak.androidgeekroom.databinding.ActivityMainBinding
import com.yusufcancakmak.androidgeekroom.db.NoteDatabase
import com.yusufcancakmak.androidgeekroom.utils.Constans.NOTE_DATABASE

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val noteDB :NoteDatabase by lazy {
        Room.databaseBuilder(this,NoteDatabase::class.java,NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private val noteAdapter by lazy { NoteAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddNote.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }
// Reflesh etkisi gibi yeniliyor
    override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem(){
        binding.apply {
        if (noteDB.dao().getAllNotes().isNotEmpty()){
            rvNoteList.visibility=View.VISIBLE
            tvEmptyText.visibility=View.GONE
            noteAdapter.differ.submitList(noteDB.dao().getAllNotes())
            setupRecyclerview()
        }else{
            rvNoteList.visibility=View.GONE
            tvEmptyText.visibility=View.VISIBLE
        }
        }
    }
    private fun setupRecyclerview(){
        binding.rvNoteList.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter=noteAdapter
        }
    }
}