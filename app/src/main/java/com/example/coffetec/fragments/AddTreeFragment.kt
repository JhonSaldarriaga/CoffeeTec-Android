package com.example.coffetec.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.coffetec.R
import com.example.coffetec.databinding.FragmentAddTreeBinding
import com.example.coffetec.model.Tree

class AddTreeFragment : Fragment() {
    var permissionsGranted: Boolean = false
    lateinit var listener: Listener
    private var _binding: FragmentAddTreeBinding? = null
    private val binding get() = _binding!!

    var tempPath: String? = null

    override fun onResume() {
        super.onResume()
        loadSpinners()
        clearFields()
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
    }

    private fun loadSpinners(){
        binding.sickTypeSpinnerAddTree.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, resources.getStringArray(R.array.tree_sicks)))
    }

    private fun setButtonsListeners(){
        binding.backBtnAddTree.setOnClickListener{ listener.onBackButtonAddTreeFragment() }
        binding.addImageButtonAddTree.setOnClickListener {
            if(permissionsGranted) listener.takeCameraPhoto()
            else listener.loadPermissions()
        }
        binding.addTreeBtn.setOnClickListener {
            //add tree to firebase
        }
    }

    fun showImageTaked(){
        val bitmap = BitmapFactory.decodeFile(tempPath)
        binding.imageCapturedAddTree.setImageBitmap(bitmap)
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
        fun addTreeToDataBase(tree: Tree)
        fun loadPermissions()
        fun takeCameraPhoto()
    }
}