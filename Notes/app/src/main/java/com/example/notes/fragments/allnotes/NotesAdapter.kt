package com.example.notes.fragments.allnotes

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.Note

//class NotesAdapter: RecyclerView.Adapter<NotesAdapter.ViewHolder>()  {
class NotesAdapter: androidx.recyclerview.widget.ListAdapter<Note, RecyclerView.ViewHolder>(DiffCallback())  {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

//    private var notesList = emptyList<Note>()
    lateinit var myListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onDeleteButtonClick(position: Int)
    }

    fun setOnClickListener(listener: OnItemClickListener){
        myListener= listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        Log.i("View type", viewType.toString())
        if (viewType == VIEW_TYPE_ONE){
            val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return NotesAdapter().CommonViewHolder(v, myListener)
        }
        else{
            val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_image, parent, false)
            return NotesAdapter().ImageViewHolder(v, myListener)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == VIEW_TYPE_ONE) {
            (holder as CommonViewHolder).noteText.text = currentList[position].noteText
        } else {
            (holder as ImageViewHolder).noteText.text = currentList[position].noteText
//            holder.image.setImageURI(currentList[position].noteImage?.toUri())
        }

    }

//    fun setData(user: List<Note>) {
//        this.notesList = user
//        notifyDataSetChanged()
//    }

    private class DiffCallback : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.noteId == newItem.noteId

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }

    inner class CommonViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView){

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

    inner class ImageViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView){

        var noteText: TextView
        var deleteButton: ImageView
        var note: View
        var image: ImageView

        init {
            noteText = itemView.findViewById(R.id.textView)
            note = itemView.findViewById(R.id.card_view)
            deleteButton = itemView.findViewById(R.id.deleteButton)
            image = itemView.findViewById(R.id.savedImage)

            note.setOnClickListener{
                listener.onItemClick(position = adapterPosition)
            }

            deleteButton.setOnClickListener {
                listener.onDeleteButtonClick(position = adapterPosition)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        currentList[position].noteImage?.let { Log.i("Image null", it) }
        if (currentList[position].noteImage.equals("null")){
            return 1
        }
        else{
            return 2
        }
    }

}