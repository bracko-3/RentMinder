package com.rentminder.dto

class Members (var uid: String = "", var groupId : Int = 0, var memberName : String?) {
    constructor() : this("", 0, null)
    override fun toString(): String {
        return "$groupId $memberName"
    }
}