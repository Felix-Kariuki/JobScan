package com.flexcode.jobscan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.flexcode.jobscan.MainActivity
import com.flexcode.jobscan.R
import com.flexcode.jobscan.databinding.FragmentJobDetailsBinding
import com.flexcode.jobscan.models.Job
import com.flexcode.jobscan.models.JobToSave
import com.flexcode.jobscan.viewmodel.JobViewModel
import com.google.android.material.snackbar.Snackbar


class JobDetailsFragment : Fragment(R.layout.fragment_job_details) {

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: JobDetailsFragmentArgs by navArgs()
    private lateinit var currentJob: Job
    private lateinit var viewModel: JobViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

       _binding = FragmentJobDetailsBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        currentJob = args.job!!

        setUpWebView()

        binding.fabAddFav.setOnClickListener {
            addJobToFavorite(view)
        }
    }

    private fun addJobToFavorite(view: View) {
        val job = JobToSave(
            0,
            currentJob.candidateRequiredLocation,currentJob.category,
            currentJob.companyLogoUrl,currentJob.companyName,
            currentJob.description,currentJob.id,currentJob.jobType,
            currentJob.publicationDate,currentJob.salary,currentJob.title,currentJob.url
        )

        viewModel.insertJob(job)
        Snackbar.make(view,"Job Saved Successfully",Snackbar.LENGTH_SHORT).show()
    }

    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let { loadUrl(it) }
        }

        binding.webView.settings.apply {
            javaScriptEnabled = true
            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            textZoom = 100
            blockNetworkImage = false
            loadsImagesAutomatically = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}