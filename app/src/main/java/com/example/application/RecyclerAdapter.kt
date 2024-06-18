package com.example.application

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecyclerAdapter(private val context :Context, val taskList : ArrayList<Task>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
val itemView = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false)

    return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = taskList[position]
        holder.tv_name.text =task.name
        holder.tv_details.text = task.details


        holder.btn_edit.setOnClickListener {
            var intent = Intent (context , AddTask::class.java)
            intent.putExtra(" mode" , " edit")
            intent.putExtra("id",task.id)
           // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

        }


    }
    class MyViewHolder(view : View) : ViewHolder(view){

        val tv_name = view.findViewById<TextView>(R.id.recycler_item_tv_name)
        val tv_details = view.findViewById<TextView>(R.id.recycler_item_tv_details)
        val btn_edit = view.findViewById<Button>(R.id.recycler_item_btn_edit)

    }
    override fun getItemCount(): Int {
      return   taskList.size  }
}