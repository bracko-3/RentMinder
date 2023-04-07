package com.rentminder.dto

data class Bill(var billId: String = "", var paymentId: Int = 0, var month: String = "", var rentBill: Int = 0, var energyBill: Int = 0, var waterBill: Int = 0, var wifiBill: Int = 0, var otherBill: Int = 0, var memberId: Int = 0, var totalPerson: Double = 0.0, var total: Double = 0.0) {

}
