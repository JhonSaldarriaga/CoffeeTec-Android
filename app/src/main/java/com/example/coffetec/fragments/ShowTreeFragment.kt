package com.example.coffetec.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.coffetec.R
import com.example.coffetec.databinding.FragmentShowTreeBinding
import java.util.*
import kotlin.collections.ArrayList

class ShowTreeFragment : Fragment() {

    private var _binding : FragmentShowTreeBinding? = null
    private val binding get() = _binding!!



    override fun onResume() {
        super.onResume()
        loadSpinner()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowTreeBinding.inflate(inflater,container,false)
        loadSpinner()
        var sickSelected = "Sick 2"
        setSelectionSickSpinner(sickSelected)
        return binding.root
    }

    private fun setSelectionSickSpinner(itemSelected:String){
        val sickList: ArrayList<String> = ArrayList()
        Collections.addAll(sickList, *resources.getStringArray(R.array.tree_sicks))
        binding.sickSpinnerShowTree.setText(binding.sickSpinnerShowTree.adapter.getItem(sickList.indexOf(itemSelected)).toString(),false)
    }

    private fun loadSpinner(){
        val sicks = resources.getStringArray(R.array.tree_sicks)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, sicks)
        binding.sickSpinnerShowTree.setAdapter(arrayAdapter)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShowTreeFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}