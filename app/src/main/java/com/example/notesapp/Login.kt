package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()


        val etemail = findViewById<EditText>(R.id.etEmail)
        val etpw = findViewById<EditText>(R.id.etPssword)
        val show = findViewById<TextView>(R.id.show)

        val login = findViewById<Button>(R.id.btnlogin)
        login.setOnClickListener{
            val email = etemail.text.toString().trim()
            val password = etpw.text.toString().trim()

            if (email.isEmpty()){
                etemail.error = "Email harus diisi"
                etemail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etemail.error = "Email tidak valid"
                etemail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 8){
                etpw.error = "Password minimal 8 karakter"
                etpw.requestFocus()
                return@setOnClickListener
            }

            Loginuser(email, password)
        }

        show.setOnClickListener{
            if (show.text.toString().equals("Show")){
                etpw.transformationMethod = HideReturnsTransformationMethod.getInstance()
                show.text = "Hide"
            }
            else{
                etpw.transformationMethod = PasswordTransformationMethod.getInstance()
                show.text = "Show"
            }
        }

        val btnRegister = findViewById<TextView>(R.id.create)
        btnRegister.setOnClickListener{
            Intent(this@Login, Create_Account::class.java).also {intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        val resetpw = findViewById<TextView>(R.id.forget)
        resetpw.setOnClickListener {
            Intent(this@Login, Reset_pw::class.java).also {
                startActivity(it)
            }
        }

        val back = findViewById<ImageView>(R.id.btnback)
        back.setOnClickListener{
            Intent(this@Login, OnBoard::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun Loginuser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Intent(this@Login, Home::class.java).also {
                        startActivity(it)
                    }
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            Intent(this@Login, Home::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}