package com.rentminder.dto

data class Household (val householdId : Int = 0, val houseName : String, val houseMembers : Int){
    override fun toString(): String {
        return "$houseName $houseMembers Members"
    }
}