package com.flexcode.jobscan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flexcode.jobscan.models.JobToSave
import com.flexcode.jobscan.repository.JobRepository
import kotlinx.coroutines.launch


class JobViewModel(
    app: Application,
    private val jobRepository: JobRepository
) : AndroidViewModel(app) {

    fun jobResult() =
        jobRepository.getJobResponseLiveData()

    fun searchJob(query: String?) =
        jobRepository.searchJob(query)

    fun searchResult() = jobRepository.getSearchResponseLiveData()

    fun insertJob(job: JobToSave) = viewModelScope.launch {
        jobRepository.insertJob(job)
    }

    fun deleteJob(job: JobToSave) = viewModelScope.launch {
        jobRepository.deleteJob(job)
    }

    fun getAllJobs() = jobRepository.getAllJobs()

}