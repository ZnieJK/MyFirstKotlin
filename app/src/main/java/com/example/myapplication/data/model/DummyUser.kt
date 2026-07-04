package com.example.myapplication.data.model

data class UsersResponse(
    val users: List<DummyUser> = emptyList(),
    val total: Int = 0,
    val skip: Int = 0,
    val limit: Int = 0,
)

data class DummyUser(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val gender: String,
    val hair: Hair = Hair(),
    val address: Address = Address(),
    val company: Company = Company(),
)

data class Hair(
    val color: String = "Unknown",
    val type: String = "",
)

data class Address(
    val postalCode: String = "00000",
)

data class Company(
    val department: String = "Unknown",
)
