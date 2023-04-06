package com.rentminder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rentminder.dto.Payment
import com.rentminder.service.PaymentService

class MainViewModel() : ViewModel() {
    var payments : MutableLiveData<List<Payment>> = MutableLiveData()
    var paymentService : PaymentService = PaymentService()

}