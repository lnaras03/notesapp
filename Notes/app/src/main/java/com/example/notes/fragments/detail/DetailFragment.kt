package com.example.notes.fragments.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.data.Note
import com.example.notes.data.NoteViewModel
import com.example.notes.fragments.allnotes.AllNotesFragment

class DetailFragment : Fragment() {

    lateinit var bundle: Bundle
    lateinit var fragment: View
    private lateinit var mNoteViewModel: NoteViewModel
    lateinit var noteText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = this.requireArguments()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragment = inflater.inflate(R.layout.fragment_detail, container, false)

        noteText = fragment.findViewById(R.id.detail_text)
        noteText.text = bundle.getString("noteText")

        setupSaveButton()

        return fragment
    }

    private fun setupSaveButton() {
        val saveButton: Button = fragment.findViewById(R.id.save_button)
        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        saveButton.setOnClickListener {
            val note = Note(noteText = noteText.text.toString().trim(), noteId = bundle.getInt("noteId"))
            mNoteViewModel.update(note)
            Toast.makeText(requireContext(), "Updated note", Toast.LENGTH_SHORT).show()

            val manager = parentFragmentManager
            manager.popBackStack()
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.root_container, AllNotesFragment())
            transaction.commitAllowingStateLoss()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_activity, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
        val itemLogout = menu.findItem(R.id.action_delete)
        itemLogout.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete){
            mNoteViewModel.delete(id = bundle.getInt("noteId"))
            Toast.makeText(requireContext(), "Deleted note", Toast.LENGTH_SHORT).show()

            val manager = parentFragmentManager
            manager.popBackStack()
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.root_container, AllNotesFragment())
            transaction.commitAllowingStateLoss()
            return true
        }
        else return super.onOptionsItemSelected(item)
    }

}