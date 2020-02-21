package com.example.todo.models

class Chore() {

    var id:Int? = null
    var choreName: String? = null
    var assignedBy: String? = null
    var assignedTo: String? = null
    var timeAssigned: Long? = null

    constructor(chorName:String,assignedBy:String,assignedTo:String,timeAssigned:Long,id:Int):this(){
        this.choreName = choreName
        this.assignedBy = assignedBy
        this.assignedTo = assignedTo
        this.timeAssigned = timeAssigned
        this.id = id

    }
}
