package com.example.coffetec.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.coffetec.R
import com.example.coffetec.databinding.FragmentAddTreeBinding

class AddTreeFragment : Fragment() {
    private var _binding: FragmentAddTreeBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        loadSpinners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTreeBinding.inflate(inflater,container,false)
        loadSpinners()
        return binding.root
    }

    // SPINNER FUNCTIONS ->
    private fun loadSpinners(){
        binding.sickTypeSpinnerAddTree.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, resources.getStringArray(R.array.tree_sicks)))
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddTreeFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}