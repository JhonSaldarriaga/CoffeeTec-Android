package com.example.coffetec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.coffetec.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
}