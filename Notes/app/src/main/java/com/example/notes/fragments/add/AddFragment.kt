package com.example.notes.fragments.add

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.data.Note
import com.example.notes.data.NoteViewModel
import com.example.notes.fragments.allnotes.AllNotesFragment
import com.example.notes.fragments.allnotes.NotesAdapter
import java.io.File
import java.util.jar.Manifest

private const val PERMISSION_CODE = 1001
private const val IMAGE_CHOOSE = 1000
private var imageUri: Uri? = null
private const val REQUEST_CODE = 13
//private lateinit var filePhoto: File

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
        setupCameraButton()

        return fragment
    }

    private fun setupCameraButton() {
        val cameraButton = fragment.findViewById<Button>(R.id.button_camera)
        cameraButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    ActivityCompat.requestPermissions(requireActivity(), permissions, PERMISSION_CODE)
                }
            }
            chooseImageGallery()
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)
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
                val note = Note(noteText = noteText.text.toString().trim(), noteImage = imageUri.toString())
                mNoteViewModel.insert(note)
                Toast.makeText(requireContext(), "Added note ${imageUri.toString()}", Toast.LENGTH_SHORT).show()

                val manager = parentFragmentManager
                manager.popBackStack()
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.root_container, AllNotesFragment())
                transaction.commitAllowingStateLoss()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseImageGallery()
                } else {
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        val viewImage = fragment.findViewById<ImageView>(R.id.viewImage);
        imageUri = intent?.data
        viewImage.setImageURI(imageUri)
//        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
//            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
//            viewImage.setImageBitmap(takenPhoto)
//        }
//        else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }

    }

}