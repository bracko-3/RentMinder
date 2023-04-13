package com.rentminder.dto

class Members (var uid: String = "", var groupId : Int = 0, var memberName : String?) {
    override fun toString(): String {
        return "$groupId $memberName"
    }
}