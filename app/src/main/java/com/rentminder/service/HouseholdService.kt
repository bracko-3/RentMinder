package com.rentminder.service

import com.rentminder.RetrofitClientInstance
import com.rentminder.dao.IHouseholdDAO
import com.rentminder.dto.Household
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class HouseholdService {
    private val service: IHouseholdDAO by lazy {
        RetrofitClientInstance.retrofitInstance?.create(IHouseholdDAO::class.java)
            ?: throw IllegalStateException("RetrofitClientInstance is null")
    }

    suspend fun fetchHouseholdsList() : List<Household>{
        return withContext(Dispatchers.IO) {
            try {
                service.getAllHouseholds().awaitResponse().body() ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}