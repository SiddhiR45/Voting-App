package com.example.voting_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Votting_page : AppCompatActivity() {
    private lateinit var rg: RadioGroup
    private lateinit var voteButton: Button
    private lateinit var db: FirebaseFirestore
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_votting_page)
        db = FirebaseFirestore.getInstance()
        username = intent.getStringExtra("username") ?: ""
        rg = findViewById(R.id.rg)
        voteButton = findViewById(R.id.vote)
        voteButton.setOnClickListener {
            val selectedOption = getSelectedOption()

            if (selectedOption.isNotEmpty()) {
                storeVote(selectedOption)
            } else {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getSelectedOption(): String {
        val checkedRadioButtonId = rg.checkedRadioButtonId
        return when (checkedRadioButtonId) {
            R.id.r1 -> "CANDIDATE 1"
            R.id.r2 -> "CANDIDATE 2"
            R.id.r3 -> "CANDIDATE 3"
            R.id.r4 -> "CANDIDATE 4"
            else -> ""
        }
    }

    private fun storeVote(selectedOption: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("votes")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        val vote = hashMapOf(
                            "userId" to userId,
                            "username" to username,
                            "candidate" to selectedOption
                        )
                        db.collection("votes")
                            .add(vote)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Vote registered successfully!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Failed to register vote", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "You have already voted", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to check vote status", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}
