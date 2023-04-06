package com.rentminder.dto

data class SavedPayment(var savedId: String = "", var paymentId: Int = 0, var month: String = "", var rentB: Int = 0, var energyB: Int = 0, var waterB: Int = 0, var wifiB: Int = 0, var otherB: Int = 0, var memberId: Int = 0, var paymentType: String = "", var totalPerson: Double = 0.0, var total: Double = 0.0) {

}
