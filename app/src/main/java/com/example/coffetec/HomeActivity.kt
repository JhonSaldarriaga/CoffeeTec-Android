package com.example.coffetec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coffetec.databinding.ActivityHomeBinding
import com.example.coffetec.fragments.*
import com.example.coffetec.model.Tree
import com.example.coffetec.recycler.TreesViewHolder
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity(), TreesFragment.Listener, TreesViewHolder.Listener, ShowTreeFragment.Listener {

    private lateinit var harvestFragment: HarvestFragment
    private lateinit var profileFragment : ProfileFragment
    private lateinit var treesFragment: TreesFragment
    private lateinit var addTreesFragment: AddTreeFragment
    private lateinit var showTreeFragment: ShowTreeFragment
    private val binding : ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        harvestFragment = HarvestFragment.newInstance()
        profileFragment = ProfileFragment.newInstance()

        //trees_section fragments
        treesFragment = TreesFragment.newInstance()
        treesFragment.listener = this
        treesFragment.listenerViewHolder = this
        addTreesFragment = AddTreeFragment.newInstance()
        showTreeFragment = ShowTreeFragment.newInstance()
        showTreeFragment.listener = this

        loadTrees()

        binding.navigator.setOnItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.harvestmenu -> {showFragment(harvestFragment)}
                R.id.homemenu -> {showFragment(profileFragment)}
                R.id.treemenu -> {showFragment(treesFragment)}
            }
            true
        }
    }

    private fun loadTrees(){
        val trees = ArrayList<Tree>()
        Firebase.firestore.collection("trees").whereEqualTo("state", resources.getStringArray(R.array.tree_states)[0]).get()
            .addOnCompleteListener{ task ->
            for(document in task.result!!) {
                val treeFound = document.toObject(Tree::class.java)
                trees.add(treeFound)
            }
            treesFragment.trees = trees
            treesFragment.loadComplete()
        }
    }

    private fun showFragment (fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.homeFragment.id, fragment)
        transaction.commit()
    }

    // OBSERVER!!!!
    override fun onClickAddTreeButton() { showFragment(addTreesFragment) }
    override fun onClickShowTree(tree: Tree) {
        showTreeFragment.tree = tree
        Log.e(">>>","Estoy en el home y recibi a: ${tree.name}")
        showFragment(showTreeFragment)
    }
    override fun onBackButtonShowTreeFragment() { showFragment(treesFragment) }
    override fun updateTreeInfo(tree: Tree) {
        if(tree.state!=resources.getStringArray(R.array.tree_states)[0]) treesFragment.removeTree(tree)
        Firebase.firestore.collection("trees").document(tree.id).set(tree).addOnSuccessListener {
            Toast.makeText(this, R.string.successfull_update, Toast.LENGTH_SHORT).show()
        }
    }
}