package com.rentminder.dto

class Members (var membersId : Int = 0, var memberName : String) {
    override fun toString(): String {
        return "$memberName"
    }
}