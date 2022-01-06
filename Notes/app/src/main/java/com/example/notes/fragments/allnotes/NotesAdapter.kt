package com.example.notes.fragments.allnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.Note

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.ViewHolder>()  {

    private var notesList = emptyList<Note>()
    lateinit var myListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onDeleteButtonClick(position: Int)
    }

    fun setOnClickListener(listener: OnItemClickListener){
        myListener= listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return NotesAdapter().ViewHolder(v, myListener)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteText.text = notesList[position].noteText
    }

    fun setData(user: List<Note>) {
        this.notesList = user
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView){

        var noteText: TextView
        var deleteButton: ImageView
        var note: View

        init {
            noteText = itemView.findViewById(R.id.textView)
            note = itemView.findViewById(R.id.card_view)
            deleteButton = itemView.findViewById(R.id.deleteButton)

            note.setOnClickListener{
                listener.onItemClick(position = adapterPosition)
            }

            deleteButton.setOnClickListener {
                listener.onDeleteButtonClick(position = adapterPosition)
            }
        }
    }

}