package com.example.application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.application.R

class MainActivity : AppCompatActivity() {
    lateinit var rv :RecyclerView
    lateinit var btn_add_task :Button
    lateinit var myDatabaseHelper : DatabaseHelper
    lateinit var myAdapter : RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv =findViewById(R.id.recycler_view)
        btn_add_task =findViewById(R.id.main_btn_add_task)

       // val taskList = ArrayList<Task>()
        //taskList.add(Task(0,"ahmed","watch"))

       fetchData()


        btn_add_task.setOnClickListener {
            var i = Intent(this,AddTask::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
        }
    }

    fun fetchData(){
        myDatabaseHelper = DatabaseHelper(this)
        myAdapter = RecyclerAdapter(applicationContext ,myDatabaseHelper.getAll())
        Log.i(" MAINACTIVITY " ,"${myAdapter.itemCount}")

        rv.layoutManager  = LinearLayoutManager(this)
        rv.adapter = myAdapter
       // myAdapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        myAdapter.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        myAdapter.notifyDataSetChanged()
    }

}