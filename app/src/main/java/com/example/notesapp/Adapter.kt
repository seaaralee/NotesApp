package com.example.notesapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Adapter(val mCtx: Context, val layoutResId: Int, val  list: List<Users>)
    : ArrayAdapter<Users>(mCtx,layoutResId,list){

    private lateinit var auth : FirebaseAuth

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)



        val textNote = view.findViewById<TextView>(R.id.textNote)
        val textDelete = view.findViewById<TextView>(R.id.TextDelete)
        val textEdit = view.findViewById<TextView>(R.id.textEdit)
        
       



        val user = list[position]
        textEdit.setOnClickListener {
            shoUpdateDialog(user)
        }

        textNote.text = user.note
        textDelete.setOnClickListener {
            Deleteinfo(user)
        }



        return view
    }

    private fun shoUpdateDialog(user: Users) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.activity_delete, null)

        val textNote = view.findViewById<EditText>(R.id.textNote)

        textNote.setText(user.note)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("USERS")

            val note = textNote.text.toString().trim()



            val user = Users(user.id,note)

            dbUsers.child(user.id).setValue(user).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()

    }


    private fun Deleteinfo(user: Users) {
        val progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("USERS")
        mydatabase.child(user.id).removeValue()
        Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, Notes1::class.java)
        context.startActivity(intent)


    }

}