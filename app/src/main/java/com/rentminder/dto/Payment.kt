package com.rentminder.dto

data class Payment (var paymentId: Int = 0, var month: String = "", var rentBill: Int = 0, var energyBill: Int = 0, var waterBill: Int = 0, var wifiBill: Int = 0, var otherBill: Int = 0, var memberId: Int = 0, var paymentType: String = "", var totalPerson: Double = 0.0, var total: Double = 0.0) {
    /*
     other types of variables that could be included isPaid: Boolean, val mateShares: Map<String, Double>?, val previousMonthsTotal: Double?,val timeStamp: LocalDateTime
     */
    override fun toString(): String {
        return "$paymentType - $memberId"
    }
}