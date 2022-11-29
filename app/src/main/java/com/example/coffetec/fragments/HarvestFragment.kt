package com.example.coffetec.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffetec.*
import com.example.coffetec.databinding.FragmentHarvestBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class HarvestFragment : Fragment(), HarvestAdapter.OnClickHarvestListener {

    private var _binding : FragmentHarvestBinding? = null
    private val binding  get() = _binding!!

    private val adapter = HarvestAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHarvestBinding.inflate(inflater, container, false)

        //recrear estado
        Util.initRecycler(binding.harvestsRecycler, requireActivity(), LinearLayoutManager.VERTICAL,).adapter = adapter
        adapter.clear()
        loadHarvests()
        adapter.onClickHarvestListener = this

        binding.addHarvest.setOnClickListener {
            val intent = Intent(this.context, NewHarvest::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun loadHarvests(){
        Log.d(">>>>>>>>>>>>>>>>>>","load")
        Firebase.firestore.collection("harvests").get()
            .addOnCompleteListener { harvest->
                for(i in harvest.result!!){
                    Log.d(">>>>>>>>>>>",i.toString())
                    adapter.addHarvest(i.toObject(Harvest::class.java))
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = HarvestFragment()
    }

    override fun openInfoHarvest(id: String) {
        val intent = Intent(activity, ShowHarvestActivity::class.java).apply {
            putExtra("idHarvest", id)
        }
        startActivity(intent)
    }
}