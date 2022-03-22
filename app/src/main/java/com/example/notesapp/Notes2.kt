package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Notes2 : AppCompatActivity() {

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes2)

        ref = FirebaseDatabase.getInstance().getReference("USERS")

        val btnAdd = findViewById<ImageView>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            savedata()

            Intent(this@Notes2, Notes1::class.java).also {
                startActivity(it)
            }
        }

        val back = findViewById<ImageView>(R.id.btnback)
        back.setOnClickListener {
            Intent(this@Notes2, Notes1::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun savedata() {
        val input = findViewById<EditText>(R.id.input)
        val note = input.text.toString()


        val userId = ref.push().key.toString()
        val user = Users(userId, note)


        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            input.setText("")
        }
    }
}