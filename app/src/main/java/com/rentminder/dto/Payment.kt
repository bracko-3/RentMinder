package com.rentminder.dto

data class Payment (
    var paymentId: Int = 0,
    var month : String = "",
    var rentBill : Double = 0.0,
    var electricBill : Double = 0.0,
    var waterBill : Double = 0.0,
    var wifiBill : Double = 0.0,
    var otherBill : Double = 0.0
)