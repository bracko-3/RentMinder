package com.rentminder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.rentminder.dto.Payment
import com.rentminder.service.PaymentService

class MainViewModel : ViewModel() {

    fun save(payment: Payment) {

    }

    private lateinit var firestore : FirebaseFirestore

}