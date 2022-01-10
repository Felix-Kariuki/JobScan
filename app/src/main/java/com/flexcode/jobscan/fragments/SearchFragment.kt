package com.flexcode.jobscan.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexcode.jobscan.MainActivity
import com.flexcode.jobscan.R
import com.flexcode.jobscan.adapters.JobAdapter
import com.flexcode.jobscan.common.Constants
import com.flexcode.jobscan.databinding.FragmentSearchBinding
import com.flexcode.jobscan.viewmodel.JobViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: JobViewModel
    private lateinit var jobAdapter: JobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Constants.isNetWorkAvailable(requireContext())
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        ) {
            searchJob()
        }else {
            Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()

        }
    }

    private fun searchJob() {
        var job: kotlinx.coroutines.Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchJob(editable.toString())
                    }
                }
            }
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        jobAdapter = JobAdapter()
        binding.rvSearchJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = jobAdapter
        }

        viewModel.searchResult().observe(viewLifecycleOwner, { job ->
            jobAdapter.differ.submitList(job.jobs)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}