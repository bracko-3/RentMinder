package com.rentminder.service

import com.rentminder.RetrofitClientInstance
import com.rentminder.dao.IHouseholdDAO
import com.rentminder.dto.Household
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class HouseholdService {
    suspend fun fetchHouseholdsList() : List<Household>?{
        return withContext(Dispatchers.IO) {
            val service = RetrofitClientInstance.retrofitInstance?.create(IHouseholdDAO::class.java)
            val houseHolds = async { service?.getAllCountries() }
            return@withContext houseHolds.await()?.awaitResponse<ArrayList<Household>>()?.body()
        }
    }
}