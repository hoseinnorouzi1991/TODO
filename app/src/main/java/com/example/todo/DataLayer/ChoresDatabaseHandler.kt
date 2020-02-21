package com.example.todo.DataLayer

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import com.example.todo.models.*
import java.text.DateFormat
import java.util.*

class ChoresDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    var context = context
    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_CHORE_TABLE = "CREATE TABLE ${TABLE_NAME} " +
                "(${KEY_CHORE_ID} INTEGER PRIMARY KEY," +
                "${KEY_CHORE_NAME} TEXT," +
                "${KEY_CHORE_ASSIGNED_BY} TEXT," +
                "${KEY_CHORE_ASSIGNED_TO} TEXT," +
                "${KEY_CHORE_ASSIGNED_TIME} LONG)"

        db?.execSQL(CREATE_CHORE_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")

        onCreate(db)
    }

    fun createChore(chore: Chore) {
        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        db.insert(TABLE_NAME, null, values)
        db.close()
        Log.d("DATA INSERTِ", "SUCCESS")
    }

    fun getAChore(id: Int): Chore? {
        var db: SQLiteDatabase = writableDatabase
        var cursor = db.query(
            TABLE_NAME, arrayOf(
                KEY_CHORE_ID,
                KEY_CHORE_NAME,
                KEY_CHORE_ASSIGNED_TO,
                KEY_CHORE_ASSIGNED_BY,
                KEY_CHORE_ASSIGNED_TIME
            ), KEY_CHORE_ID + "=?", arrayOf(id.toString()),
            null,
            null,
            null
        )

        if(cursor != null)
        {
            cursor.moveToFirst()
            var chore = Chore()
            chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
            chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
            chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
            chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

            var dateFormat = DateFormat.getDateInstance()
            var formattedDate = dateFormat.format(Date(cursor.getLong(cursor.getColumnIndex(
                KEY_CHORE_ASSIGNED_TIME))).time)

            return chore
        }

        return null

    }

    fun updateChore(chore:Chore) : Int
    {
        var db: SQLiteDatabase = writableDatabase
        var values:ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME,chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY,chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO,chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME,System.currentTimeMillis())


        //update a row from database
        return  db.update(TABLE_NAME,values, KEY_CHORE_ID +"=?", arrayOf(chore.id.toString()))
    }

    fun deleteChore(chore: Chore)
    {
        var db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_CHORE_ID+"=?", arrayOf(chore.id.toString()))
        db.close()
    }

    fun getChoresCount():Int{
        var db:SQLiteDatabase = readableDatabase
        var countQuery = "SELECT * from "+ TABLE_NAME
        var cursor:Cursor = db.rawQuery(countQuery,null)
        return  cursor.count
    }
}