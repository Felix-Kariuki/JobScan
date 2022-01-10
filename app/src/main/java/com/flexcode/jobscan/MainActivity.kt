package com.flexcode.jobscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.flexcode.jobscan.databinding.ActivityMainBinding
import com.flexcode.jobscan.db.JobDatabase
import com.flexcode.jobscan.repository.JobRepository
import com.flexcode.jobscan.viewmodel.JobViewModel
import com.flexcode.jobscan.viewmodel.JobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: JobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        setUpViewModel()
    }


    private fun setUpViewModel() {
        val jobRepository = JobRepository(JobDatabase(this))

        val viewModelProviderFactory = JobViewModelFactory(application,jobRepository)

        viewModel = ViewModelProvider(
            this,viewModelProviderFactory
        ).get(JobViewModel::class.java)

    }
}