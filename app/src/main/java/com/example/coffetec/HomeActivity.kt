package com.example.coffetec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coffetec.databinding.ActivityHomeBinding
import com.example.coffetec.fragments.HarvestFragment
import com.example.coffetec.fragments.ProfileFragment
import com.example.coffetec.fragments.ShowTreeFragment
import com.example.coffetec.fragments.TreesFragment
import com.example.coffetec.model.Tree
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity(), ShowTreeFragment.Listener {

    private lateinit var harvestFragment: HarvestFragment
    private lateinit var profileFragment : ProfileFragment
    private lateinit var treesFragment: TreesFragment
    private lateinit var showTreeFragment: ShowTreeFragment
    private val binding : ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        harvestFragment = HarvestFragment.newInstance()
        profileFragment = ProfileFragment.newInstance()

        //trees_section fragments
        treesFragment = TreesFragment.newInstance()
        showTreeFragment = ShowTreeFragment.newInstance()
        showTreeFragment.listener = this
        showTreeFragment.tree = Tree("nwo1ISmEsi8YmAWumGoJ", "Antracnosis", "arbolin", "12/11/2022","Enfermo", -97.0943923, 48.233123)

        binding.navigator.setOnItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.harvestmenu -> {showFragment(harvestFragment)}
                R.id.homemenu -> {showFragment(profileFragment)}
                R.id.treemenu -> {showFragment(showTreeFragment)}
            }
            true
        }
    }

    private fun showFragment (fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.homeFragment.id, fragment)
        transaction.commit()
    }

    // OBSERVER!!!!
    override fun onBackButtonShowTreeFragment() { showFragment(treesFragment) }
    override fun updateTreeInfo(tree: Tree) {
        Firebase.firestore.collection("trees").document(tree.id).set(tree).addOnSuccessListener {
            Toast.makeText(this, R.string.successfull_update, Toast.LENGTH_SHORT).show()
        }
    }
}