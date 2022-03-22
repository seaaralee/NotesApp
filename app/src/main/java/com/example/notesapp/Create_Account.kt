package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class Create_Account : AppCompatActivity() {


    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        auth = FirebaseAuth.getInstance()

        val etemail = findViewById<EditText>(R.id.Email)
        val etpw = findViewById<EditText>(R.id.etPssword)
        val show = findViewById<TextView>(R.id.show)

        val btnSignUp = findViewById<Button>(R.id.btnCreateAcc)
        btnSignUp.setOnClickListener{


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

            registerUser(email, password)
        }

        val masuk = findViewById<TextView>(R.id.Join)
        masuk.setOnClickListener{
            Intent(this@Create_Account, Login::class.java).also {
                startActivity(it)
            }
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

        val back = findViewById<ImageView>(R.id.btnback)
        back.setOnClickListener{
            Intent(this@Create_Account, OnBoard::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Intent(this@Create_Account, Created::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }else
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            Intent(this@Create_Account, Home::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}