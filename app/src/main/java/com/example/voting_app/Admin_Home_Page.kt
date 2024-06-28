package com.example.voting_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Admin_Home_Page : AppCompatActivity() {

    private lateinit var t1:TextView
    private lateinit var t2:TextView
    private lateinit var t3:TextView
    private lateinit var t4:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home_page)
        loadVotingData()
    }

    private fun loadVotingData() {
        t1=findViewById(R.id.t1)
        t2=findViewById(R.id.t2)
        t3=findViewById(R.id.t3)
        t4=findViewById(R.id.t4)
        var db: FirebaseFirestore
        db= FirebaseFirestore.getInstance()
        db.collection("votes")
            .get()
            .addOnSuccessListener { querySnapshot ->
                var count1 = 0
                var count2 = 0
                var count3 = 0
                var count4 = 0

                for (document in querySnapshot.documents) {
                    val candidate = document.getString("candidate")
                    if (candidate != null) {
                        when (candidate) {
                            "CANDIDATE 1" -> count1++
                            "CANDIDATE 2" -> count2++
                            "CANDIDATE 3" -> count3++
                            "CANDIDATE 4" -> count4++
                        }
                    }
                }
                t1.text = "CANDIDATE 1: $count1 votes"
                t2.text = "CANDIDATE 2: $count2 votes"
                t3.text = "CANDIDATE 3: $count3 votes"
                t4.text = "CANDIDATE 4: $count4 votes"
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Failed to retrieve voting data",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}