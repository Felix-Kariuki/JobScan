package com.flexcode.jobscan.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.flexcode.jobscan.MainActivity
import com.flexcode.jobscan.R
import com.flexcode.jobscan.adapters.JobAdapter
import com.flexcode.jobscan.common.Constants
import com.flexcode.jobscan.databinding.FragmentRemoteJobsBinding
import com.flexcode.jobscan.viewmodel.JobViewModel


class RemoteJobsFragment : Fragment(R.layout.fragment_remote_jobs),
    SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentRemoteJobsBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobViewModel: JobViewModel
    private lateinit var jobAdapter: JobAdapter
    private lateinit var swipeLayout: SwipeRefreshLayout
    private var page = 1
    private var limit = 20

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRemoteJobsBinding.inflate(inflater,container,false)

        swipeLayout = binding.swipeContainer
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(
            Color.GREEN, Color.RED,
            Color.BLUE, Color.CYAN
        )

        swipeLayout.post {
            swipeLayout.isRefreshing = true
            setUpRecyclerView()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jobViewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()

        binding.swipeContainer.setOnRefreshListener {
            fetchData()
        }
    }

    private fun setUpRecyclerView() {
        jobAdapter = JobAdapter()
        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(
                object : DividerItemDecoration (
                    activity, LinearLayoutManager.VERTICAL
                ){}
            )
            adapter = jobAdapter
        }

        fetchData()
    }

    private fun fetchData() {
        activity?.let {
            if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Constants.isNetWorkAvailable(requireActivity())
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            ) {

                jobViewModel.jobResult()
                    .observe(viewLifecycleOwner, { job ->
                        if (job != null) {
                            jobAdapter.differ.submitList(job.jobs)
                            swipeLayout.isRefreshing = false
                        }
                    })
            } else {
                Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
                swipeLayout.isRefreshing = false
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRefresh() {
        setUpRecyclerView()
    }


}