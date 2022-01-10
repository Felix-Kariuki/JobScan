package com.flexcode.jobscan.api

import com.flexcode.jobscan.models.RemoteJob
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JobApi {

    @GET("remote-jobs")
    fun getJob(): Call<RemoteJob>

    @GET("remote-jobs")
    fun searchJob(@Query("search") query: String?): Call<RemoteJob>
}