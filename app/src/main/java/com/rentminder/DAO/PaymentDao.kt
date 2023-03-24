package com.rentminder.DAO



import com.rentminder.DTO.PaymentDto
import retrofit2.Call
import retrofit2.http.GET

interface PaymentDao {
    @GET("")
    suspend fun  getAllPayments(): ArrayList<PaymentDto>


}