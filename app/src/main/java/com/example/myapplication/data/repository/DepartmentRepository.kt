package com.example.myapplication.data.repository

import com.example.myapplication.data.model.DepartmentSummary
import com.example.myapplication.data.model.DummyUser
import com.example.myapplication.data.remote.DummyJsonApi
import com.example.myapplication.data.remote.RetrofitClient

class DepartmentRepository(
    private val api: DummyJsonApi = RetrofitClient.api,
) {

    suspend fun fetchDepartmentSummaries(): List<DepartmentSummary> {
        val response = api.getUsers(limit = 0)
        return buildSummaries(response.users)
    }

    private fun buildSummaries(users: List<DummyUser>): List<DepartmentSummary> {
        val byDepartment = users.groupBy { it.company.department.ifBlank { "Unknown" } }

        return byDepartment.map { (department, deptUsers) ->
            val ages = deptUsers.map { it.age }

            val hairSummary = deptUsers
                .groupingBy { it.hair.color.ifBlank { "Unknown" } }
                .eachCount()

            // Keyed as "FirstNameLastName" -> postalCode, same as seed.ts's addressUser map.
            val addressUsers = deptUsers.associate { user ->
                "${user.firstName}${user.lastName}" to user.address.postalCode.ifBlank { "00000" }
            }

            DepartmentSummary(
                departmentName = department,
                maleCount = deptUsers.count { it.gender == "male" },
                femaleCount = deptUsers.count { it.gender == "female" },
                ageMin = ages.minOrNull() ?: 0,
                ageMax = ages.maxOrNull() ?: 0,
                hairSummary = hairSummary,
                addressUsers = addressUsers,
            )
        }.sortedBy { it.departmentName }
    }
}