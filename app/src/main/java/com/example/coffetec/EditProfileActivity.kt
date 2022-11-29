package com.example.coffetec

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MainThread
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.coffetec.databinding.ActivityEditProfileBinding
import com.example.coffetec.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    private var URI:String = ""
    private var file:File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            withContext(Dispatchers.Main){
                binding.nameEditProfileET.setText(user.fullname)
                binding.phoneEditProfileET.setText(user.phone)
                binding.houseCampEditProfileET.setText(user.houseCamp)
                Firebase.storage.getReference().child("users_photos").child(user.uriProfile).downloadUrl.addOnSuccessListener {
                    Glide.with(binding.imgProfile).load(it).into(binding.imgProfile)
                }
            }
        }

        val cameralauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onCameraResult)
        val gallerylauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onGalleryResult)
        binding.cameraButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setView(R.layout.dialog_img_profile)
            val dialog = builder.create()
            dialog.setTitle("Cambia tu foto de perfil")
            dialog.setIcon(R.drawable.caficultor)
            dialog.show()


            var camera: Button = dialog.findViewById(R.id.cameraBtnDialog)
            camera.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                file = File("${getExternalFilesDir(null)}/photo.png")
                val uri = FileProvider.getUriForFile(this, packageName,file!!)
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
                URI = uri.toString()
                cameralauncher.launch(intent)
                dialog.dismiss()
            }


            var gallery: Button = dialog.findViewById(R.id.galleryBtnDialog)
            gallery.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                gallerylauncher.launch(intent)
                dialog.dismiss()
            }
        }

        binding.saveBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val user = Firebase.firestore
                    .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                    .toObject(User::class.java)!!

                val uid = Firebase.auth.currentUser?.uid
                Log.e(
                    "<--<<<", "${uid}"
                )
                val filename = UUID.randomUUID().toString()
                uid?.let {
                    val newUser = User(
                        it,
                        binding.nameEditProfileET.text.toString(),
                        binding.houseCampEditProfileET.text.toString(),
                        user.document,
                        user.email,
                        binding.phoneEditProfileET.text.toString(),
                        filename
                    )
                    Firebase.storage.getReference().child("users_photos").child(filename).putFile(Uri.parse(URI)).await()
                    Firebase.firestore.collection("users").document(it).set(newUser)
                }

            }
            val builder = AlertDialog.Builder(this)
            builder.setView(R.layout.dialog_save_profile)
            val dialog = builder.create()
            dialog.setTitle("Cambios exitosos")
            dialog.setIcon(R.drawable.caficultor)
            dialog.show()

            var accept: Button = dialog.findViewById(R.id.saveBtnDialog)
            accept.setOnClickListener {
                dialog.dismiss()
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()
            }
        }

        binding.backBtnEditProfile.setOnClickListener {
            showDialogBack()
        }
    }

    fun showDialogBack(){
        val builder = AlertDialog.Builder(this)
        builder.setView(R.layout.dialog_back_profile)
        val dialog = builder.create()
        dialog.setTitle("Salir sin guardar")
        dialog.setIcon(R.drawable.caficultor)
        dialog.show()

        var accept: Button = dialog.findViewById(R.id.acceptBtnBack)
        accept.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this,ProfileActivity::class.java))
            finish()
        }

        var cancel: Button = dialog.findViewById(R.id.cancelBtnBack)
        cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun showDialogImgProfile(){

    }

    fun onCameraResult(result: ActivityResult){
        // val bitmap = result.data?.extras?.get("data") as Bitmap
        // binding.photoIMG.setImageBitmap(bitmap)
        if(result.resultCode == Activity.RESULT_OK){
            val bitmap = BitmapFactory.decodeFile(file?.path)
            val thumball = Bitmap.createScaledBitmap(bitmap, bitmap.width/4,bitmap.height/4,true)
            val uriImage = Uri.parse(URI)
            binding.imgProfile.setImageURI(uriImage)
        }else if(result.resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(this,"No tomo la foto", Toast.LENGTH_SHORT).show()
        }
    }

    fun onGalleryResult(result: ActivityResult){
        if(result.resultCode == Activity.RESULT_OK){
            URI = result.data?.data.toString()
            val uriImage = Uri.parse(URI)
            binding.imgProfile.setImageURI(uriImage)
        }
    }
}