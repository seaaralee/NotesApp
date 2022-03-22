package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.*

class Notes1 : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Users>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes1)

        val back = findViewById<ImageView>(R.id.btnback)
        back.setOnClickListener {
            Intent(this@Notes1, Home::class.java).also {
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
                        val user = h.getValue(Users::class.java)
                        list.add(user!!)
                    }
                    val adapter = Adapter(this@Notes1,R.layout.notes,list)
                    listView.adapter = adapter
                }
            }
        })

        val nota = findViewById<ImageView>(R.id.btnAdd)
        nota.setOnClickListener {
            Intent(this@Notes1, Notes2::class.java).also {
                startActivity(it)
            }
        }
    }

}