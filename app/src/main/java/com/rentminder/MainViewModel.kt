package com.rentminder

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.rentminder.dto.Bill
import com.rentminder.dto.Payment

class MainViewModel() : ViewModel() {
    val cal: Calendar = Calendar.getInstance()
    val monthDate = SimpleDateFormat("MMMM")
    val monthName: String = monthDate.format(cal.time)
    var payments : MutableLiveData<List<Payment>> = MutableLiveData()
    var selectedPayment by mutableStateOf(Bill())

    private lateinit var firestore : FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun save() {
        val document =
            if (selectedPayment.billId == null || selectedPayment.billId.isEmpty()) {
                firestore.collection("Payments").document()
            } else {
                firestore.collection("Payments").document(selectedPayment.billId)
            }
        selectedPayment.billId = document.id
        val handle = document.set(selectedPayment)
        handle.addOnSuccessListener { Log.d("Firebase", "Document saved") }
        handle.addOnFailureListener { Log.e("Firebase", "Save failed $it") }
    }

}