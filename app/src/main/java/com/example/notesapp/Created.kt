package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Created : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_created)

        val login = findViewById<Button>(R.id.login)
        login.setOnClickListener {
            Intent(this@Created, Login::class.java).also {
                startActivity(it)
            }
        }
    }
}