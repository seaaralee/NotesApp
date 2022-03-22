package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class OnBoard : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board)

        val btnSignIn = findViewById<Button>(R.id.login)
        btnSignIn.setOnClickListener{
            Intent(this@OnBoard, Login::class.java).also {
                startActivity(it)
            }
        }

        val btnSignUp = findViewById<Button>(R.id.btnCreateAcc)
        btnSignUp.setOnClickListener{
            Intent(this@OnBoard, Create_Account::class.java).also {
                startActivity(it)
            }
        }

    }
    override fun onStart() {
        super.onStart()

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null){
            Intent(this@OnBoard, Home::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}