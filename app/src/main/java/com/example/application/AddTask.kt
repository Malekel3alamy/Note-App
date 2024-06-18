package com.example.application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class AddTask : AppCompatActivity() {

    lateinit private var et_name :TextView
    lateinit private var et_details :TextView
    lateinit private var btn_save:Button
    lateinit private var btn_delete:Button
    lateinit private var myDatabaseHelper: DatabaseHelper
     private var isModeEdit : Boolean = false
    var mode:String? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        et_name = findViewById(R.id.add_task_et_name)
        et_details = findViewById(R.id.add_task_et_details)
        btn_save = findViewById(R.id.add_task_btn_save)
        btn_delete = findViewById(R.id.add_task_btn_delete)

             mode = intent.getStringExtra(" mode")
            val id = intent.getIntExtra("id",0)
            if(mode == " edit"){
                // edit mode
                if(id >= 0){
                    isModeEdit = true
                    myDatabaseHelper = DatabaseHelper(this)
                    val task = myDatabaseHelper.getTask(id)
                    et_name.text = task.name
                    et_details.text = task.details
                    btn_save.setText("save")
                    btn_delete.isVisible = true
                }
            }else {
                // add mode
                isModeEdit = false
                btn_save.setText("Add Task")
                btn_delete.isVisible = false
            }

        btn_save.setOnClickListener {


               val name = et_name.text.toString()
               val details = et_details.text.toString()
            val task = Task(id,name,details)
               if(isModeEdit){
                   // update database
                    myDatabaseHelper = DatabaseHelper(this)
                   val success = myDatabaseHelper.updateTask(task)
                   if(success){
                       // Successful Update
                         mode == null
                       var i = Intent(this,MainActivity::class.java)
                       startActivity(i)

                   }else{
                        // failed to update database

                       Toast.makeText(this,"failed to update database ",Toast.LENGTH_SHORT).show()
                   }
               }else{
                   // add task to database
                   myDatabaseHelper = DatabaseHelper(this)

                   val success = myDatabaseHelper.addTask(task)

                   if (success){
                       // successfully add task
                       Toast.makeText(this,"success  to add Task to  database ",Toast.LENGTH_SHORT).show()
                       var i = Intent(this,MainActivity::class.java)
                       startActivity(i)

                   }else{
                       // failed to add task
                       Toast.makeText(this,"failed to add Task to  database ",Toast.LENGTH_SHORT).show()
                   }

               }

        }
        btn_delete.setOnClickListener {

            if (et_name.text.isNotEmpty()&& et_details
                    .text.isNotEmpty()){
                val success = myDatabaseHelper.deleteTask(id)
                if(success){
                    // deleted successfully
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }else{
                    // failed to delete
                    Toast.makeText(this,"failed to delete Task from  database ",Toast.LENGTH_SHORT).show()

                }
            }else{
                Toast.makeText(this,"Please Input Data ",Toast.LENGTH_SHORT).show()

            }

        }

    }
}