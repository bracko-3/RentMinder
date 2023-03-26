package com.rentminder.dto

data class Members (val membersId : Int = 0, val memberName : String) {
    override fun toString(): String {
        return "$membersId $memberName"
    }
}