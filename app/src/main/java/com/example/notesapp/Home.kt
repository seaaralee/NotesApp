package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class Home : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private var backPressedTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        val btnlogout = findViewById<ImageView>(R.id.btnLogout)

        btnlogout.setOnClickListener {
            auth.signOut()
            val int = Intent(this, Login::class.java)
            startActivity(int)
        }

        val akun = findViewById<ImageView>(R.id.pp)
        akun.setOnClickListener {
            Intent(this@Home, Account::class.java).also {
                startActivity(it)
            }
        }

        val user = auth.currentUser

        val name = findViewById<TextView>(R.id.name)
        val pp = findViewById<ImageView>(R.id.pp)

        if (user != null) {
            if (user.photoUrl != null) {
                Picasso.get().load(user.photoUrl).into(pp)
            } else {
                Picasso.get().load("https://picsum.photos/id/316/200").into(pp)
            }
        }

        name.setText(user?.displayName)

        val note = findViewById<ImageView>(R.id.catatan)
        note.setOnClickListener {
            Intent(this@Home, Notes1::class.java).also {
                startActivity(it)
            }
        }

        val task = findViewById<ImageView>(R.id.tugas)
        task.setOnClickListener {
            Intent(this@Home, Tasks1::class.java).also {
                startActivity(it)
            }
        }

    }

    override fun onBackPressed() {
        if (backPressedTime + 2000> System.currentTimeMillis()) {
            super.onBackPressed()
        }else{
            Toast.makeText(applicationContext, "Tekan kembali untuk keluar", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}