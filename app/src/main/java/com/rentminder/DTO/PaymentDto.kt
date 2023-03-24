package com.rentminder.DTO

import java.time.LocalDateTime

data class PaymentDto (var paymentId: Int = 0, var memberId: Int, var paymentType: String, var paymentNum: Double){
    /*
     other types of variables that could be included isPaid: Boolean, val mateShares: Map<String, Double>?, val previousMonthsTotal: Double?,val timeStamp: LocalDateTime
     */
    override fun toString(): String {
        return "$paymentNum - $paymentType - $memberId"
    }

}