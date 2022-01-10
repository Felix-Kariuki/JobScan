package com.flexcode.jobscan.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flexcode.jobscan.api.RetrofitInstance
import com.flexcode.jobscan.db.JobDatabase
import com.flexcode.jobscan.models.JobToSave
import com.flexcode.jobscan.models.RemoteJob
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobRepository(private val db:JobDatabase) {

    private val jobService = RetrofitInstance.api
    private val jobResponseLiveData: MutableLiveData<RemoteJob> = MutableLiveData()
    private val searchJobLiveData: MutableLiveData<RemoteJob> = MutableLiveData()

    init {
        getJobResponse()
    }

    private fun getJobResponse() {
        jobService.getJob().enqueue(
            object : Callback<RemoteJob> {
                override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                    if (response.body() != null){
                        jobResponseLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                    jobResponseLiveData.postValue(null)
                    Log.d("error abc",t.message.toString())
                }
            }
        )
    }

    fun searchJob(query: String?){
        jobService.searchJob(query).enqueue(
            object : Callback<RemoteJob> {
                override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                    if (response.body() != null){
                        searchJobLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                    searchJobLiveData.postValue(null)
                    Log.d("error abc",t.message.toString())
                }
            }
        )
    }

    fun getJobResponseLiveData(): LiveData<RemoteJob> {
        return jobResponseLiveData
    }

    fun getSearchResponseLiveData(): LiveData<RemoteJob> {
        return searchJobLiveData
    }

    suspend fun insertJob(job: JobToSave) = db.getJobDao().insertJob(job)
    suspend fun deleteJob(job: JobToSave) = db.getJobDao().deleteJob(job)
    fun getAllJobs() = db.getJobDao().getAllJob()
}