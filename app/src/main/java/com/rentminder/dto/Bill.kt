package com.rentminder.dto

data class Bill(var billId: String = "", var month: String = "", var rentBill: Int = 0, var rentPaid: Boolean = false, var electricPaid: Boolean = false, var waterPaid: Boolean = false, var wifiPaid: Boolean = false, var otherPaid: Boolean = false, var energyBill: Int = 0, var waterBill: Int = 0, var wifiBill: Int = 0, var otherBill: Int = 0, var memberId: String = "", var totalPerson: Double = 0.0, var total: Double = 0.0) {

}
