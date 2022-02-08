package com.example.notes.fragments.allnotes

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.NoteViewModel
import com.example.notes.fragments.add.AddFragment
import com.example.notes.fragments.detail.DetailFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllNotesFragment : Fragment() {


    private lateinit var adapter: NotesAdapter
    private lateinit var mUserviewModel: NoteViewModel
    lateinit var fragmentView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.fragment_all_notes, container, false)

        setupRecyclerView()
        setupAddFab()

        // Inflate the layout for this fragment
        return fragmentView

    }

    private fun setupRecyclerView() {
        val recyclerViewNotes: RecyclerView = fragmentView.findViewById(R.id.noteRecyclerView)

        adapter = NotesAdapter()
        recyclerViewNotes.adapter = adapter
        mUserviewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        adapter.setOnClickListener(object: NotesAdapter.OnItemClickListener {

            override fun onItemClick(position: Int) {
                val allNotes = mUserviewModel.allNotes.value
                val selectedNoteDetails = allNotes?.get(position)

                val bundle = bundleOf("noteId" to selectedNoteDetails?.noteId,
                    "noteText" to selectedNoteDetails?.noteText,
                    "noteImage" to selectedNoteDetails?.noteImage)
                val detailFragment = DetailFragment()
                detailFragment.arguments = bundle
                val manager = parentFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.root_container, detailFragment)
                transaction.addToBackStack(null)
                transaction.setReorderingAllowed(true)
                transaction.commit()
            }

            override fun onDeleteButtonClick(position: Int) {
                val allNotes = mUserviewModel.allNotes.value
                val selectedNoteID = allNotes?.get(position)?.noteId
                if (selectedNoteID != null) {
                    mUserviewModel.delete(selectedNoteID)
                    Toast.makeText(requireContext(), "Deleted note", Toast.LENGTH_SHORT).show()
                }
            }
        })

        //UserViewModel
        mUserviewModel.allNotes.observe(viewLifecycleOwner, Observer { note ->
            adapter.submitList(note)
        })

    }

    private fun setupAddFab() {

        val nextFab: FloatingActionButton = fragmentView.findViewById(R.id.addButton)
        nextFab.setOnClickListener{
            val manager = parentFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.root_container, AddFragment())
            transaction.addToBackStack(null)
            transaction.setReorderingAllowed(true)
            transaction.commitAllowingStateLoss()
        }

    }
}