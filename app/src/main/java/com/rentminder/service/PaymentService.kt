package com.rentminder.service
import com.rentminder.RetrofitClientInstance
import com.rentminder.dao.PaymentDao
import com.rentminder.dto.Payment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
class PaymentService {
    suspend fun fetchPayment(): List<Payment>? {
        return withContext(Dispatchers.IO) {
            val service = RetrofitClientInstance.retrofitInstance?.create(PaymentDao::class.java)
            val payments = async { service?.getAllPayments() }
            var result = payments.await()?.awaitResponse()?.body()
            return@withContext result
        }
    }
}