package com.rentminder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentminder.dto.Payment
import com.rentminder.service.PaymentService
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    var payments : MutableLiveData<List<Payment>> = MutableLiveData()
    var paymentService : PaymentService = PaymentService()

    fun fetchPayments() {
        viewModelScope.launch {
            var innerPayments = paymentService.fetchPayment()
            payments.postValue(innerPayments)
        }
    }

}