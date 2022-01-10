package com.flexcode.jobscan.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexcode.jobscan.MainActivity
import com.flexcode.jobscan.R
import com.flexcode.jobscan.adapters.JobSavedAdapter
import com.flexcode.jobscan.databinding.FragmentSavedJobsBinding
import com.flexcode.jobscan.models.JobToSave
import com.flexcode.jobscan.viewmodel.JobViewModel


class SavedJobsFragment : Fragment(R.layout.fragment_saved_jobs),
    JobSavedAdapter.OnItemClickListener {

    private var _binding: FragmentSavedJobsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: JobViewModel
    private lateinit var jobSavedAdapter: JobSavedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedJobsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        jobSavedAdapter = JobSavedAdapter(this)

        binding.rvSavedJobs.apply {

            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(
                object : DividerItemDecoration(
                    activity, LinearLayout.VERTICAL
                ) {})
            adapter = jobSavedAdapter
        }

        viewModel.getAllJobs().observe(viewLifecycleOwner, { jobToSave ->
            jobSavedAdapter.differ.submitList(jobToSave)
            updateUI(jobToSave)
        })
    }

    private fun updateUI(jobToSave: List<JobToSave>) {

        if (jobToSave.isNotEmpty()) {
            binding.rvSavedJobs.visibility = View.VISIBLE
        } else {
            binding.rvSavedJobs.visibility = View.VISIBLE
        }
    }


    override fun onItemClick(job: JobToSave, view: View, position: Int) {
        deleteJob(job)
    }

    private fun deleteJob(job: JobToSave) {

        AlertDialog.Builder(activity).apply {
            setTitle("Delete Job")
            setMessage("Do you want to permanently delete This Job?")
            setPositiveButton("DELETE"){_,_ ->
                viewModel.deleteJob(job)
                Toast.makeText(activity,"Job Deleted",Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
