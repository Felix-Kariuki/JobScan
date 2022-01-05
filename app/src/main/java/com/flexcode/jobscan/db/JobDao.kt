package com.flexcode.jobscan.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flexcode.jobscan.models.JobToSave

@Dao
interface JobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job:JobToSave): Long

    @Query("SELECT * FROM job ORDER BY id DESC")
    fun getAllJob(): LiveData<List<JobToSave>>

    @Delete
    suspend fun deleteJob(job: JobToSave)
}