package com.rentminder.dto

class Household (var householdId : Int = 0, var houseName : String, var houseMembers : Int){
    override fun toString(): String {
        return "$houseName $houseMembers Members"
    }
}