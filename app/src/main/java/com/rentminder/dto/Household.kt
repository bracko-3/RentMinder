package com.rentminder.dto

data class Household (var householdId : Int = 0, var houseName : String, var houseMembers : Int){
    override fun toString(): String {
        return "$houseName $houseMembers Members"
    }
}