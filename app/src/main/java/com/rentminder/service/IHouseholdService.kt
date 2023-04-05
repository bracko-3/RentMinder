package com.rentminder.service

import com.rentminder.RetrofitClientInstance
import com.rentminder.dao.IHouseholdDAO
import com.rentminder.dto.Household
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class IHouseholdService {
    suspend fun fetchHouseholds() : List<Household>?{
        return withContext(Dispatchers.IO) {
            val service = RetrofitClientInstance.retrofitInstance?.create(IHouseholdDAO::class.java)
            val country = async { service?.getAllCountries() }
            return@withContext country.await()?.awaitResponse<ArrayList<Household>>()?.body()
        }
    }
}