package com.example.myapplication.data.remote

import com.example.myapplication.data.model.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyJsonApi {

    @GET("users")
    suspend fun getUsers(@Query("limit") limit: Int = 0): UsersResponse
}