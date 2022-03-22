package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class Reset_pw : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pw)

        val reset = findViewById<Button>(R.id.reset)
        reset.setOnClickListener{
            val etmail = findViewById<EditText>(R.id.etmail)
            val email = etmail.text.toString().trim()

            if (email.isEmpty()){
                etmail.error = "Email harus diisi"
                etmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etmail.error = "Email tidak valid"
                etmail.requestFocus()
                return@setOnClickListener
            }
        }
        val back = findViewById<ImageView>(R.id.btnback)
        back.setOnClickListener {
            Intent(this@Reset_pw, Login::class.java).also {
                startActivity(it)
            }
        }
    }
}