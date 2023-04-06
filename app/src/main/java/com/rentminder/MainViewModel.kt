package com.rentminder

import androidx.lifecycle.MutableLiveData
import com.rentminder.dto.Payment

class MainViewModel {
    var payments : MutableLiveData<List<Payment>> = MutableLiveData()
    var paymentService : PaymentService = PaymentService()

    
}