package com.rentminder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.rentminder.dto.Payment

class MainViewModel() : ViewModel() {
    var payments : MutableLiveData<List<Payment>> = MutableLiveData()
    private lateinit var firestore : FirebaseFirestore

    fun save() {

    }

}