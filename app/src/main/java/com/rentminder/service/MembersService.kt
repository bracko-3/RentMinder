package com.rentminder.service

import com.rentminder.RetrofitClientInstance
import com.rentminder.dao.MembersDAO
import com.rentminder.dto.Members
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class MembersService {
    suspend fun fetchMembers() : List<Members>? {
        return withContext(Dispatchers.IO){
            val service = RetrofitClientInstance.retrofitInstance?.create(MembersDAO::class.java)
            val members = async {service?.getAllMembers()?.awaitResponse()?.body() }
            return@withContext members?.await()?: emptyList()
        }
    }
}