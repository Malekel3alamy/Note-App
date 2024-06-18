package com.example.application

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DatabaseHelper(context : Context) : SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION) {
    companion object {
        private const val DB_NAME = "tasksDatabase"
        private const val DB_VERSION= 2
        private const val TABLE_NAME = "tasksList"
        private const val ID = "id"
        private const val NAME = "name"
        private const val DETAILS = "details"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME ($ID INTEGER primary key AUTOINCREMENT  ," +
                " $NAME TEXT not null , $DETAILS TEXT  not null )")
        Log.i("CURSOR"," CREATE ")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       db?.execSQL(" DROP TABLE IF EXISTS $TABLE_NAME ")
        onCreate(db)

    }

    @SuppressLint("Range")
    fun getAll() : ArrayList<Task> {
        val arrayTask = ArrayList<Task>()
        val db = writableDatabase

        val cursor = db.rawQuery( "SELECT * FROM $TABLE_NAME ",null)
if(cursor != null){
    if (  cursor.moveToFirst()){
        do {
            val id : Int  = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
             Log.i(" TEST" , "$id ")
            val name = cursor.getString(cursor.getColumnIndex(NAME))
            val details = cursor.getString(cursor.getColumnIndex(DETAILS))
            val task = Task(id,name,details)

            arrayTask.add(task)

        }while (cursor.moveToNext())

    }
}
        cursor.close()
        return arrayTask
    }
    fun addTask(  task :Task) : Boolean {
        Log.i("TEST "," success")
        val db = this.writableDatabase  // we should use this here
        val values = ContentValues()

        values.put(NAME,task.name)
        values.put(DETAILS,task.details)
        val success = db.insert(TABLE_NAME,null,values)

        db.close()
        return (Integer.parseInt(success.toString()) != -1)
    }

    fun deleteTask(id : Int ) : Boolean{
        val db = writableDatabase
        val success = db.delete(TABLE_NAME," $ID=? ", arrayOf(id.toString())) .toLong()
         db.close()
        return (success.toInt() != -1)
    }
    fun updateTask(task : Task) : Boolean {
        val db =writableDatabase
        val values = ContentValues()
        values.put(ID , task.id)
        values.put(NAME,task.name)
        values.put(DETAILS,task.details)
        val success = db.update(TABLE_NAME,values," $ID =? ", arrayOf(task.id.toString())) .toLong()
        db.close()
        return (success.toInt() != -1)
    }

    @SuppressLint("Range")
    fun getTask(id : Int) : Task{
        val db =writableDatabase
        val cursor = db.rawQuery(" SELECT * FROM $TABLE_NAME WHERE $ID =? ", arrayOf(id.toString()))
        cursor.moveToFirst()
        val name = cursor.getString(cursor.getColumnIndex(NAME))
        val details = cursor.getString(cursor.getColumnIndex(DETAILS))
        val task = Task(id,name,details)
        db.close()
        return task
    }

}