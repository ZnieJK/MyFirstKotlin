package com.example.myapplication.Page.navigation

sealed class Screen(val route: String, val label: String) {
    data object Home : Screen("home", "Home")
    data object Question1 : Screen("question1", "Question 1")
    data object Question2 : Screen("question2", "Question 2")

    companion object {
        val all = listOf(Home, Question1, Question2)
    }
}