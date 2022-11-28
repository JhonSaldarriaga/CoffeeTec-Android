package com.example.coffetec

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.coffetec.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestPermissions(arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ,1)

        binding.signInBtn.setOnClickListener{
            if(binding.emailSignInET.text.toString() != "" && binding.passwordSignInET.editText!!.text.toString() != ""){
                Firebase.auth.signInWithEmailAndPassword(
                    binding.emailSignInET.text.toString(),
                    binding.passwordSignInET.editText!!.text.toString()
                ).addOnSuccessListener {
                    val user = it.user!!
                    Log.e(" 27- LoginActivity ", user.uid)
                    val intent = Intent(this,HomeActivity::class.java).apply {
                        putExtra("userId", user.uid)
                    }
                    startActivity(intent)
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this,it.message, Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"Existen campos vacios", Toast.LENGTH_LONG).show()
            }
        }

        binding.signUpTxt.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            var allGrant = true
            for (result in grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) allGrant = false
            }
            if(!allGrant){
                Toast.makeText(this,"Por favor acepte los permisos", Toast.LENGTH_LONG).show()
            }
        }
    }
}