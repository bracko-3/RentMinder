package com.rentminder

import androidx.lifecycle.MutableLiveData
import com.rentminder.dto.Payment
import com.rentminder.service.PaymentService

class MainViewModel {
    var payments : MutableLiveData<List<Payment>> = MutableLiveData()
    var paymentService : PaymentService = PaymentService()


}