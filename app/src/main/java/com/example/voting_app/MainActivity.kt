package com.example.voting_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var firebaseAuth: FirebaseAuth
        val log=findViewById<Button>(R.id.Login)
        FirebaseApp.initializeApp(this)
        firebaseAuth=Firebase.auth
        log.setOnClickListener {
            val uname:String=findViewById<EditText>(R.id.uname).text.toString().trim()
            val pswd:String=findViewById<EditText>(R.id.pswd).text.toString().trim()
           if(uname.isEmpty()&&pswd.isEmpty())
           {
               Toast.makeText(this,"Please fill all the feilds",Toast.LENGTH_LONG).show()
           }
            else
           {
               firebaseAuth.signInWithEmailAndPassword(uname,pswd)
                   .addOnCompleteListener(this) { task ->
                       if (task.isSuccessful) {
                           Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                           val user = firebaseAuth.currentUser
                           if (user != null) {
                               if (user.email == "admin@example.com") {
                                   val i=Intent(applicationContext,Admin_Home_Page::class.java)
                                   startActivity(i)
                               } else {
                                   val i=Intent(applicationContext,Votting_page::class.java)
                                   startActivity(i)
                               }
                           }
                       } else {
                           Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                       }
                   }
           }
        }
        val reg=findViewById<Button>(R.id.Register)
        reg.setOnClickListener {
            val i=Intent(this,Register_page::class.java)
            startActivity(i)
        }
    }
}