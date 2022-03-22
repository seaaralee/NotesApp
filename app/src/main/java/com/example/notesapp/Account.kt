package com.example.notesapp

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class Account : AppCompatActivity() {

    companion object{
        const val REQUEST_CAMERA =100
    }

    private lateinit var imageuri : Uri

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        auth = FirebaseAuth.getInstance()

        val back = findViewById<ImageView>(R.id.btnback)
        back.setOnClickListener {
            Intent(this@Account, Home::class.java).also {
                startActivity(it)
            }
        }

        val user = auth.currentUser
        val ivProfile = findViewById<ImageView>(R.id.pp)
        val etName = findViewById<EditText>(R.id.etName)
        val etemail = findViewById<EditText>(R.id.etemail)
        val verified = findViewById<ImageView>(R.id.icverified)
        val unverified = findViewById<ImageView>(R.id.icunverified)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnUpdate = findViewById<ImageView>(R.id.btnSave)

        if (user != null){
            if (user.photoUrl != null){
                Picasso.get().load(user.photoUrl).into(ivProfile)
            }else{
                Picasso.get().load("https://picsum.photos/id/316/200").into(ivProfile)
            }

            etName.setText(user.displayName)
            etemail.setText(user.email)

            if (user.isEmailVerified){
                verified.visibility = View.VISIBLE
            }else{
                unverified.visibility = View.VISIBLE
            }

            if (user.phoneNumber.isNullOrEmpty()){
                etPhone.setText("Masukkan nomor telepon")
            }else{
                etPhone.setText(user.phoneNumber)
            }
        }


        ivProfile.setOnClickListener{
            IntentCamera()
        }

        btnUpdate.setOnClickListener {
            val image = when{
                ::imageuri.isInitialized -> imageuri
                user?.photoUrl == null -> Uri.parse("https://picsum.photos/id/316/200")
                else -> user.photoUrl
            }

            val name = etName.text.toString().trim()

            if (name.isEmpty()){
                etName.error = "Nama harus diisi"
                etName.requestFocus()
                return@setOnClickListener
            }

            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(image)
                .build().also {
                    user?.updateProfile(it)?.addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

        unverified.setOnClickListener {
            user?.sendEmailVerification()?.addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Email verifikasi telah dikirim", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun IntentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(this?.packageManager). also {
                startActivityForResult(intent, REQUEST_CAMERA)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK){
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("img/${FirebaseAuth.getInstance().currentUser?.uid}")

        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()
        val ivProfile = findViewById<ImageView>(R.id.pp)

        ref.putBytes(image)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    ref.downloadUrl.addOnCompleteListener {
                        it.result?.let {
                            imageuri = it
                            ivProfile.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }


    }

}