package com.example.coffetec

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.coffetec.databinding.ActivityHomeBinding
import com.example.coffetec.fragments.*
import com.example.coffetec.model.Tree
import com.example.coffetec.recycler.TreesViewHolder
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(), TreesFragment.Listener, TreesViewHolder.Listener, AddTreeFragment.Listener, ShowTreeFragment.Listener {

    private lateinit var harvestFragment: HarvestFragment
    private lateinit var profileFragment : ProfileFragment
    private lateinit var treesFragment: TreesFragment
    private lateinit var addTreesFragment: AddTreeFragment
    private lateinit var showTreeFragment: ShowTreeFragment
    private val binding : ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    private val cameraLauncher  = registerForActivityResult(StartActivityForResult(), ::onCamaraResult)
    private var tempFile: File? = null

    @RequiresApi(Build.VERSION_CODES.M)
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
        addTreesFragment.listener = this
        showTreeFragment = ShowTreeFragment.newInstance()
        showTreeFragment.listener = this

        loadPermissions()
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
    //trees fragment
    override fun onClickAddTreeButton() { showFragment(addTreesFragment) }
    override fun onClickShowTree(tree: Tree) {
        showTreeFragment.tree = tree
        Log.e(">>>","Estoy en el home y recibi a: ${tree.name}")
        showFragment(showTreeFragment)
    }
    //add_trees fragment
    override fun onBackButtonAddTreeFragment() { showFragment(treesFragment) }
    override fun addTreeToDataBase(tree: Tree) {
        TODO("Not yet implemented")
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun loadPermissions(){
        requestPermissions(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ),1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){
            var allGrant = true
            for(result in grantResults){
                Log.e(">>>", "$result")
                if(result == PackageManager.PERMISSION_DENIED) allGrant = false
            }
            if(allGrant){
                addTreesFragment.permissionsGranted = true
            }
            else{ Toast.makeText(this,"No se han aceptado los servicios de la cámara",Toast.LENGTH_SHORT).show() }
        }
    }

    override fun takeCameraPhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        tempFile = File("${getExternalFilesDir(null)}/photo${UUID.randomUUID().toString()}.png")
        val uri = FileProvider.getUriForFile(this, packageName, tempFile!!)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        cameraLauncher.launch(intent)
    }

    private fun onCamaraResult(result: ActivityResult){
        if(result.resultCode == RESULT_OK){
            val path = tempFile?.path
            addTreesFragment.tempPath = path
            addTreesFragment.showImageTaked()
        }else if(result.resultCode==RESULT_CANCELED){
            Toast.makeText(this,"No se detectó una foto tomada", Toast.LENGTH_SHORT).show()
        }
    }
    //show_trees fragment
    override fun onBackButtonShowTreeFragment() { showFragment(treesFragment) }
    override fun updateTreeInfo(tree: Tree) {
        if(tree.state!=resources.getStringArray(R.array.tree_states)[0]) treesFragment.removeTree(tree)
        Firebase.firestore.collection("trees").document(tree.id).set(tree).addOnSuccessListener {
            Toast.makeText(this, R.string.successfull_update, Toast.LENGTH_SHORT).show()
        }
    }

}