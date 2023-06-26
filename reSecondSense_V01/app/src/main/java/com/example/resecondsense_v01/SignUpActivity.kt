package com.example.resecondsense_v01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.resecondsense_v01.databinding.ActivitySignupPageBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignupPageBinding
    var dbhelper = DataContext
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_signup_page)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener {
            val Username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()
            val confirmPassword = binding.txtconfirmPassword.text.toString()

            if (Username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(Username, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {

                            it.exception?.printStackTrace()
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            Log.e("MYAPP", "exception", it.exception);
                        }
                    }


                } else {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show() }
             }else {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
             }
        }
        //directs back to sign in page


    }
}