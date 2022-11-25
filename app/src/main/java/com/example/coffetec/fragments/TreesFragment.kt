package com.example.coffetec.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffetec.databinding.FragmentTreesBinding


class TreesFragment : Fragment() {

    private var _binding: FragmentTreesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTreesBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TreesFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}