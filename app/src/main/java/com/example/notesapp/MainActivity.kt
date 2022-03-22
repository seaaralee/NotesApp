package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity: AppCompatActivity() {

    //Variabels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animations
        val topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        val bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        val Notes = findViewById<ImageView>(R.id.note);
        val Pencil = findViewById<ImageView>(R.id.pencil);

        Notes.setAnimation(bottomAnim);
        Pencil.setAnimation(topAnim);

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@MainActivity, OnBoard::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}