package com.example.coffetec.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffetec.databinding.FragmentTreesBinding
import com.example.coffetec.model.Tree
import com.example.coffetec.recycler.TreesAdapter
import com.example.coffetec.recycler.TreesViewHolder


class TreesFragment : Fragment() {

    lateinit var listener: Listener
    private var loadState : Boolean = true
    private var _binding: FragmentTreesBinding? = null
    private val binding get() = _binding!!

    lateinit var listenerViewHolder : TreesViewHolder.Listener
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter : TreesAdapter
    var trees : ArrayList<Tree> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTreesBinding.inflate(inflater,container,false)
        binding.progressBarTrees.isVisible = loadState
        if(!loadState) {
            initButtons()
            initRecycler()
        }
        return binding.root
    }

    fun loadComplete(){
        loadState = false
        if(_binding!=null){
            binding.progressBarTrees.isVisible = loadState
            initRecycler()
        }
    }

    private fun initButtons(){
        binding.addTreeButtonTrees.setOnClickListener { listener.onClickAddTreeButton() }
    }

    private fun initRecycler(){
        layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewTrees.layoutManager = layoutManager
        binding.recyclerViewTrees.setHasFixedSize(true)
        adapter = TreesAdapter()
        adapter.listener = listenerViewHolder
        adapter.trees = trees
        binding.recyclerViewTrees.adapter = adapter
    }

    fun addTree(tree:Tree){
        adapter.addTree(tree)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TreesFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface Listener{
        fun onClickAddTreeButton()
    }
}