package com.rentminder.service

import com.rentminder.RetrofitClientInstance
import com.rentminder.dao.MembersDAO
import com.rentminder.dto.Members
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class MembersService {
    private val service: MembersDAO by lazy {
        RetrofitClientInstance.retrofitInstance?.create(MembersDAO::class.java)
            ?: throw IllegalStateException("RetrofitClientInstance is null")
    }

    suspend fun fetchMembersList(): List<Members> {
        return withContext(Dispatchers.IO) {
            try {
                service.getAllMembers().awaitResponse().body() ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}