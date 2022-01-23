package com.flexcode.jobscan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexcode.jobscan.databinding.JobLayoutBinding
import com.flexcode.jobscan.fragments.MainFragmentDirections
import com.flexcode.jobscan.models.Job
import com.flexcode.jobscan.models.JobToSave

class JobSavedAdapter constructor(
    private val itemClick: OnItemClickListener
) : RecyclerView.Adapter<JobSavedAdapter.JobViewHolder>() {

    private var binding : JobLayoutBinding? = null

    class JobViewHolder(itemBinding: JobLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object :
        DiffUtil.ItemCallback<JobToSave>() {
        override fun areItemsTheSame(oldItem: JobToSave, newItem: JobToSave): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JobToSave, newItem: JobToSave): Boolean {
            return newItem == oldItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        binding = JobLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,
            false)
        return JobViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val currentJob = differ.currentList[position]

        holder.itemView.apply {

            Glide.with(this)
                .load(currentJob.companyLogoUrl)
                .into(binding?.ivCompanyLogo!!)

            binding?.tvCompanyName?.text = currentJob.companyName
            binding?.tvJobLocation?.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle?.text = currentJob.title
            binding?.tvJobType?.text = currentJob.jobType
            binding?.ibDelete?.visibility = View.VISIBLE

            val dateJob = currentJob.publicationDate!!.split("T")
            binding?.tvDate?.text = dateJob?.get(0)
        }.setOnClickListener { view ->
            val tags = arrayListOf<String>()
            val job = Job(
                currentJob.candidateRequiredLocation, currentJob.category,
                currentJob.companyLogoUrl,currentJob.companyName,
                currentJob.description,currentJob.id,currentJob.jobType,
                currentJob.publicationDate,currentJob.salary,tags,
                currentJob.title,currentJob.url
            )

            val direction = MainFragmentDirections
                .actionMainFragmentToJobDetailsFragment(job)
            view.findNavController().navigate(direction)
        }

        holder.apply {
            binding?.ibDelete?.setOnClickListener {
                itemClick.onItemClick(
                    currentJob,
                    binding?.ibDelete!!,
                    position
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener {
        fun onItemClick(
            job: JobToSave,
            view: View,
            position: Int
        )
    }
}