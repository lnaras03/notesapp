package com.example.notes.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.data.Note
import com.example.notes.data.NoteViewModel
import com.example.notes.fragments.allnotes.AllNotesFragment

class AddFragment : Fragment() {

    lateinit var fragment: View
    private lateinit var mNoteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragment =  inflater.inflate(R.layout.fragment_add, container, false)

        setupAddButton()

        return fragment
    }

    private fun setupAddButton() {
        val addButton = fragment.findViewById<Button>(R.id.button_save)
        val noteText = fragment.findViewById<TextView>(R.id.note_text)
        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        addButton.setOnClickListener {
            if (noteText.text.isBlank()){
                Toast.makeText(requireContext(),
                    "Note is empty, please fill in something to save", Toast.LENGTH_LONG).show()
            }
            else{
                val note = Note(noteText = noteText.text.toString())
                mNoteViewModel.insert(note)
                Toast.makeText(requireContext(), "Added note", Toast.LENGTH_SHORT).show()

                val manager = parentFragmentManager
                manager.popBackStack()
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.root_container, AllNotesFragment())
                transaction.commitAllowingStateLoss()
            }
        }
    }

}