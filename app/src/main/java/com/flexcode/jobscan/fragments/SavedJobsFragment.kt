package com.flexcode.jobscan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flexcode.jobscan.R
import com.flexcode.jobscan.databinding.FragmentSavedJobsBinding


class SavedJobsFragment : Fragment(R.layout.fragment_saved_jobs) {

    private var _binding: FragmentSavedJobsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedJobsBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}