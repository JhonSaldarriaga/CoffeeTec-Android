package com.example.coffetec

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffetec.databinding.FragmentSensorsDashboardBinding

class SensorsDashboardFragment : Fragment() {

    private var _binding: FragmentSensorsDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSensorsDashboardBinding.inflate(inflater, container, false)

        binding.addSensorFlotatingButton.setOnClickListener {
            val intent = Intent(activity, RegistrationSensorActivity::class.java).apply{

            }
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        fun newInstance() = SensorsDashboardFragment()
    }
}