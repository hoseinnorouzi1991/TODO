package com.example.todo.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.todo.DataLayer.ChoresDatabaseHandler
import com.example.todo.R
import com.example.todo.models.Chore

class MainActivity : AppCompatActivity() {

    var dbHandler: ChoresDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDatabaseHandler(this)

        var newChore = Chore()
        newChore.choreName = "clean room"
        newChore.assignedBy = "mohammad"
        newChore.assignedTo = "sara"


        dbHandler?.createChore(newChore)

        // Read from database

        var getChore:Chore? = dbHandler?.getAChore(1)

        Log.d("Chore Name",getChore?.choreName)
    }
}
