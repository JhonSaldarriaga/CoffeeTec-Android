package com.example.coffetec.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffetec.Harvest
import com.example.coffetec.HarvestAdapter
import com.example.coffetec.NewHarvest
import com.example.coffetec.databinding.FragmentHarvestBinding

class HarvestFragment : Fragment(), NewHarvest.OnNewHarvestListener {

    private var _binding : FragmentHarvestBinding? = null
    private val binding  get() = _binding!!

    private val adapter = HarvestAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHarvestBinding.inflate(inflater, container, false)
        val view = binding.root

        //recrear estado
        val postRecycler = binding.harvestsRecycler

        postRecycler.setHasFixedSize(true)
        postRecycler.layoutManager = LinearLayoutManager(activity)
        postRecycler.adapter = adapter

        binding.addHarvest.setOnClickListener {
            val intent = Intent(this.context, NewHarvest::class.java)
            startActivity(intent)
        }
        return view
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HarvestFragment()
    }

    override fun onNewHarvest(id:String, numBag: Int, date: String, qr: String, state: String){
        val newHarvest = Harvest(id,numBag,date,qr,state)
        adapter.addHarvest(newHarvest)
    }
}