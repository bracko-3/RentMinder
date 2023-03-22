package com.rentminder.DTO

data class Payment (var paymentId: Int, var memberId: Int,var paymentType: String, var paymentNum: Int){

    override fun toString(): String {
        return "$paymentNum - $memberId"
    }

}