package com.flexcode.jobscan.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.flexcode.jobscan.databinding.JobLayoutBinding
import com.flexcode.jobscan.fragments.MainFragmentDirections
import com.flexcode.jobscan.models.Job

class JobAdapter : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    private var binding: JobLayoutBinding? = null

    class JobViewHolder(itemBinding: JobLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)


    private val differCallBack =
        object :
            DiffUtil.ItemCallback<Job>() {
            override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
                return oldItem == newItem
            }
        }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        binding = JobLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JobViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val currentJob = differ.currentList[position]

        holder.itemView.apply {

            Glide.with(this)
                .load(currentJob.companyLogoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding!!.ivCompanyLogo)

            binding!!.tvCompanyName.text = currentJob.companyName
            binding!!.tvJobLocation.text = currentJob.candidateRequiredLocation
            binding!!.tvJobTitle.text = currentJob.title
            binding!!.tvJobType.text = currentJob.jobType

            val dateJob = currentJob.publicationDate!!.split("T")
            binding!!.tvDate.text = dateJob[0]
        }.setOnClickListener { view ->
            val direction = MainFragmentDirections
                .actionMainFragmentToJobDetailsFragment(currentJob)
            view.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}