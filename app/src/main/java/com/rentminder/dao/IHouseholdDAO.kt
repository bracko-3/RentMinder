package com.rentminder.dao

import com.rentminder.dto.Household
import retrofit2.Call
import retrofit2.http.GET

interface IHouseholdDAO {
    @GET("")
    fun getAllCountries() : Call<ArrayList<Household>>
}