package com.example.voting_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Register_page : AppCompatActivity() {
    private  lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        var firebaseAuth: FirebaseAuth
        val reg = findViewById<Button>(R.id.reg)
        FirebaseApp.initializeApp(this)
        firebaseAuth = FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

        reg.setOnClickListener {
            val uname: String = findViewById<EditText>(R.id.uname1).text.toString().trim()
            val pswd: String = findViewById<EditText>(R.id.pswd1).text.toString().trim()
            val email: String = findViewById<EditText>(R.id.email).text.toString().trim()
            val phone: String = findViewById<EditText>(R.id.phone).text.toString().trim()
            if (uname.isEmpty() || pswd.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, pswd)
                    .addOnCompleteListener { task ->
                        val user= hashMapOf(
                            "username" to uname,
                            "email" to email,
                            "phone" to phone
                        )
                        db.collection("users")
                            .add(user)
                        Toast.makeText(this,"Registeration is successfull",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                val log = findViewById<Button>(R.id.log)
                log.setOnClickListener {
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                }
            }
        }

