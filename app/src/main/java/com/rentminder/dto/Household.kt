package com.rentminder.dto

data class Household (var householdId : Int = 0, var householdName : String, var householdMembers : Int){
    override fun toString(): String {
        return "$householdName $householdMembers Members"
    }
}