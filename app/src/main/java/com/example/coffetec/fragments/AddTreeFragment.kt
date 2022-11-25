package com.example.coffetec.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffetec.R
import com.example.coffetec.databinding.FragmentAddTreeBinding

class AddTreeFragment : Fragment() {
    private var _binding: FragmentAddTreeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTreeBinding.inflate(inflater,container,false)
        return binding.root
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