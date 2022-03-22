package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.*

class Tasks1 : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Users1>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks1)

        val back = findViewById<ImageView>(R.id.btnback)
        back.setOnClickListener {
            Intent(this@Tasks1, Home::class.java).also {
                startActivity(it)
            }
        }

        ref = FirebaseDatabase.getInstance().getReference("USERS")
        list = mutableListOf()
        listView = findViewById(R.id.listView1)

        val refresh = findViewById<SwipeRefreshLayout>(R.id.refresh)

        refresh.setOnRefreshListener {
            recreate()
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    for (h in p0.children){
                        val user1 = h.getValue(Users1::class.java)
                        list.add(user1!!)
                    }
                    val adapter = Adapter1(this@Tasks1,R.layout.tasks,list)
                    listView.adapter = adapter
                }
            }
        })

        val nota = findViewById<ImageView>(R.id.btnAdd)
        nota.setOnClickListener {
            Intent(this@Tasks1, Tasks2::class.java).also {
                startActivity(it)
            }
        }
    }

}