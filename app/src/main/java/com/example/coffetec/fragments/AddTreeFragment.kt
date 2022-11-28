package com.example.coffetec.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.coffetec.R
import com.example.coffetec.databinding.FragmentAddTreeBinding
import com.example.coffetec.model.Tree
import java.time.LocalDate
import java.util.UUID

class AddTreeFragment : Fragment() {
    var permissionsGranted: Boolean = false
    lateinit var listener: Listener
    private var _binding: FragmentAddTreeBinding? = null
    private val binding get() = _binding!!

    var uri: Uri? = null

    override fun onResume() {
        super.onResume()
        loadSpinners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTreeBinding.inflate(inflater,container,false)
        loadSpinners()
        setButtonsListeners()
        clearFields()
        return binding.root
    }

    private fun clearFields(){
        binding.nameEditTextAddTree.setText("")
        binding.sickTypeSpinnerAddTree.setText("")
        binding.latitudeEditTextAddTree.setText("")
        binding.longitudeEditTextAddTree.setText("")
        uri = null
    }

    private fun loadSpinners(){
        binding.sickTypeSpinnerAddTree.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, resources.getStringArray(R.array.tree_sicks)))
    }

    @SuppressLint("NewApi")
    private fun setButtonsListeners(){
        binding.backBtnAddTree.setOnClickListener{ listener.onBackButtonAddTreeFragment() }
        binding.addImageButtonAddTree.setOnClickListener {
            if(permissionsGranted) listener.takeCameraPhoto()
            else listener.loadPermissions()
        }
        binding.addTreeBtn.setOnClickListener {
            val id = UUID.randomUUID().toString()
            val date = LocalDate.now().toString()
            val state = resources.getStringArray(R.array.tree_states)[0]
            val disease = binding.sickTypeSpinnerAddTree.text.toString()
            val name = binding.nameEditTextAddTree.text.toString()
            val latitude = binding.latitudeEditTextAddTree.text.toString().toDouble()
            val longitude = binding.longitudeEditTextAddTree.text.toString().toDouble()
            var tree = Tree(id, disease, name, date, state, latitude, longitude, uri!=null)
            listener.addTreeToDataBase(tree, uri)
            clearFields()
        }
    }

    fun showImageTaked(){
        Log.e(">>>","$uri")
        binding.imageCapturedAddTree.setImageURI(uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddTreeFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface Listener{
        fun onBackButtonAddTreeFragment()
        fun addTreeToDataBase(tree: Tree, uri:Uri?)
        fun loadPermissions()
        fun takeCameraPhoto()
    }
}