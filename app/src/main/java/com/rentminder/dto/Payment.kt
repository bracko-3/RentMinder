package com.rentminder.dto

data class Payment (val paymentId: Int = 0, val memberId: Int, val paymentType: String, val paymentNum: Double){
    /*
     other types of variables that could be included isPaid: Boolean, val mateShares: Map<String, Double>?, val previousMonthsTotal: Double?,val timeStamp: LocalDateTime
     */
    override fun toString(): String {
        return "$paymentNum - $paymentType - $memberId"
    }

}