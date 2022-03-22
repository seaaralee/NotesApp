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

class Adapter1(val mCtx: Context, val layoutResId: Int, val  list: List<Users1>)
    : ArrayAdapter<Users1>(mCtx,layoutResId,list){

    private lateinit var auth : FirebaseAuth

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)



        val textTasks = view.findViewById<TextView>(R.id.textTasks)
        val textDelete = view.findViewById<TextView>(R.id.TextDelete1)





        val user1 = list[position]

        textTasks.text = user1.tasks
        textDelete.setOnClickListener {
            Deleteinfo(user1)
        }



        return view
    }




    private fun Deleteinfo(user1: Users1) {
        val progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("USERS")
        mydatabase.child(user1.Id).removeValue()
        Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, Tasks1::class.java)
        context.startActivity(intent)


    }

}