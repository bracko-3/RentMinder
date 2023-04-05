package com.rentminder.service

import com.rentminder.RetrofitClientInstance
import com.rentminder.dao.IMembersDAO
import com.rentminder.dto.Members
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class IMembersService {
    suspend fun fetchMembers() : List<Members>? {
        return withContext(Dispatchers.IO){
            val service = RetrofitClientInstance.retrofitInstance?.create(IMembersDAO::class.java)
            val members = async {service?.getAllMembers()}
            var result =  members.await()?.awaitResponse()?.body()
            return@withContext result
        }

    }

}