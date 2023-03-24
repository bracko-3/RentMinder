package com.rentminder
import com.rentminder.RetrofitClientInstance
import com.rentminder.DAO.PaymentDao
import com.rentminder.DTO.Payment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
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