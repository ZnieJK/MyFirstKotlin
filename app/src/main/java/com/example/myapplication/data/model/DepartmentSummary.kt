package com.example.myapplication.data.model

data class DepartmentSummary(
    val departmentName: String,
    val maleCount: Int,
    val femaleCount: Int,
    val ageMin: Int,
    val ageMax: Int,
    val hairSummary: Map<String, Int>,
    val addressUsers: Map<String, String>,
)
