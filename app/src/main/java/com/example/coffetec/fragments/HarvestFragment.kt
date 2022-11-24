package com.example.coffetec.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffetec.R
import com.example.coffetec.databinding.FragmentHarvestBinding

class HarvestFragment : Fragment() {

    private var _binding : FragmentHarvestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_harvest, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HarvestFragment()
    }
}