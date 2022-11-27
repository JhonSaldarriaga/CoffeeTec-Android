package com.example.coffetec.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.coffetec.R
import com.example.coffetec.databinding.FragmentShowTreeBinding
import com.example.coffetec.model.Tree
import java.util.*
import kotlin.collections.ArrayList

class ShowTreeFragment : Fragment() {
    // [0] -> DISEASES
    // [1] -> STATES
    private var spinnersInFragment = listOf("DISEASES","STATES")
    private var _binding : FragmentShowTreeBinding? = null
    private val binding get() = _binding!!
    lateinit var listener: Listener
    lateinit var tree: Tree

    override fun onResume() {
        super.onResume()
        loadSpinners()
        loadTreeInformation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowTreeBinding.inflate(inflater,container,false)
        loadSpinners()
        loadState(false)
        setButtonsListeners()
        loadTreeInformation()
        return binding.root
    }

    // INITIALIZE FUNCTIONS ->
    private fun loadState(isEditing:Boolean){
        binding.cancelEditBtnShowTree.isVisible = isEditing
        binding.confirmEditBtnShowTree.isVisible = isEditing
        binding.editBtnShowTree.isInvisible = isEditing
        binding.nameEditTextShowTree.isEnabled = isEditing
        binding.sickSpinnerLayoutShowTree.isEnabled = isEditing
        binding.treeStateSpinnerLayoutShowTree.isEnabled = isEditing
    }

    private fun setButtonsListeners(){
        binding.backBtnShowTree.setOnClickListener{ listener.onBackButtonShowTreeFragment() }
        binding.editBtnShowTree.setOnClickListener { loadState(true) }
        binding.confirmEditBtnShowTree.setOnClickListener {
            loadState(false)
            tree.name = binding.nameEditTextShowTree.text.toString()
            tree.disease = binding.sickSpinnerShowTree.text.toString()
            tree.state = binding.treeStateSpinnerShowTree.text.toString()
            Log.e(">>>","${tree.name}, ${tree.disease}, ${tree.state}")
            listener.updateTreeInfo(tree)
        }
        binding.cancelEditBtnShowTree.setOnClickListener {
            loadState(false)
            loadTreeInformation()
        }
    }

    private fun loadTreeInformation(){
        Log.e(">>>", "Soy el fragment y voy a cargar a ${tree.name}")
        binding.idEditTextShowTree.setText(tree.id)
        setSelectionSpinner(tree.disease, 0)
        binding.nameEditTextShowTree.setText(tree.name)
        binding.dateEditTextShowTree.setText(tree.date)
        setSelectionSpinner(tree.state, 1)
        binding.locationTextShowTree.text = "LAT: ${tree.latitude}, LON: ${tree.longitude}"
    }

    // SPINNER FUNCTIONS ->
    private fun loadSpinners(){
        binding.sickSpinnerShowTree.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, resources.getStringArray(R.array.tree_sicks)))
        binding.treeStateSpinnerShowTree.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, resources.getStringArray(R.array.tree_states)))
    }

    private fun setSelectionSpinner(itemSelected:String, spinner_index:Int){
        val list: ArrayList<String> = ArrayList()
        when(spinnersInFragment[spinner_index]){
            spinnersInFragment[0] -> {
                Collections.addAll(list, *resources.getStringArray(R.array.tree_sicks))
                binding.sickSpinnerShowTree.setText(binding.sickSpinnerShowTree.adapter.getItem(list.indexOf(itemSelected)).toString(),false)
            }
            spinnersInFragment[1] -> {
                Collections.addAll(list, *resources.getStringArray(R.array.tree_states))
                binding.treeStateSpinnerShowTree.setText(binding.treeStateSpinnerShowTree.adapter.getItem(list.indexOf(itemSelected)).toString(),false)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShowTreeFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface Listener{
        fun onBackButtonShowTreeFragment()
        fun updateTreeInfo(tree:Tree)
    }
}