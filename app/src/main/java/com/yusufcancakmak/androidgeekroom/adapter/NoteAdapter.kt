package com.yusufcancakmak.androidgeekroom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yusufcancakmak.androidgeekroom.databinding.ItemNoteBinding
import com.yusufcancakmak.androidgeekroom.db.NoteEntity
import com.yusufcancakmak.androidgeekroom.ui.UpdateActivity
import com.yusufcancakmak.androidgeekroom.utils.Constans.BUNDLE_NOTE_ID

class NoteAdapter :RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemNoteBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var currentlist=differ.currentList[position]
        holder.binding.tvTitle.text=currentlist.noteTitle.toString()
        holder.binding.tvDesc.text=currentlist.noteDesc.toString()
        holder.binding.root.setOnClickListener {
            val intent=Intent(holder.itemView.context,UpdateActivity::class.java)
            intent.putExtra(BUNDLE_NOTE_ID,currentlist.noteId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount()=differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}
