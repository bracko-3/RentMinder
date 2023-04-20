package com.rentminder.dao

import com.rentminder.dto.Payment
import retrofit2.Call
import retrofit2.http.GET

interface IPaymentDao {
    @GET("")
    suspend fun  getAllPayments(): Call<ArrayList<Payment>>
}