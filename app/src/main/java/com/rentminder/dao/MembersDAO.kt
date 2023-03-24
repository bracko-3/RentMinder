package com.rentminder.dao

import com.rentminder.dto.Members
import retrofit2.Call
import retrofit2.http.GET

interface MembersDAO {
    @GET("")

    fun getAllMembers() : Call<ArrayList<Members>>

}


