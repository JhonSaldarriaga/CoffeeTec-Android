package com.example.coffetec

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.coffetec.databinding.ActivityProfileBinding
import com.example.coffetec.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch(Dispatchers.IO) {
            val user = Firebase.firestore
                .collection("users").document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!

            withContext(Dispatchers.Main){
                binding.nameTxt.text = user.fullname
                binding.documentTxt.text = user.document
                binding.phoneTxt.text = user.phone
                binding.houseCampTxt.text = user.houseCamp
                binding.emailTxt.text = user.email
                Firebase.storage.reference.child("users_photos").child(user.uriProfile).downloadUrl.addOnSuccessListener {
                    Glide.with(binding.imgProfile).load(it).into(binding.imgProfile)
                }
            }
        }

        binding.editProfileButton.setOnClickListener {
            startActivity(Intent(this,EditProfileActivity::class.java))
            finish()
        }

        binding.backBtnProfile.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }
}