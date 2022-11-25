package com.example.coffetec.sensors

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffetec.databinding.FragmentSensorsDashboardBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SensorsDashboardFragment : Fragment(), SensorAdapter.OnClickSensorListener {

    private var _binding: FragmentSensorsDashboardBinding? = null
    private val binding get() = _binding!!
    val adapter = SensorAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSensorsDashboardBinding.inflate(inflater, container, false)
        Util.initRecycler(binding.sensorsRecyclerView, requireActivity(), LinearLayoutManager.VERTICAL,).adapter = adapter
        adapter.clear()
        loadSensors()

        adapter.onClickSensorListener = this
        binding.addSensorFlotatingButton.setOnClickListener {
            val intent = Intent(activity, RegistrationSensorActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun loadSensors() {
        Firebase.firestore.collection("sensors").get()
            .addOnCompleteListener { sensor ->
                for (doc in sensor.result!!) {
                    adapter.addSensor(doc.toObject(Sensor::class.java))
                }
            }
    }

    companion object {
        fun newInstance() = SensorsDashboardFragment()
    }

    override fun openInfoSensor(id: String) {
        val intent = Intent(activity, InfoSensorActivity::class.java).apply{
            putExtra("idSensor", id)
        }
        startActivity(intent)
    }
}