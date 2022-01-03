package com.flexcode.jobscan.models

data class JobX(
    val candidate_required_location: String,
    val category: String,
    val company_logo_url: String,
    val company_name: String,
    val description: String,
    val id: Int,
    val job_type: String,
    val publication_date: String,
    val salary: String,
    val tags: List<String>,
    val title: String,
    val url: String
)