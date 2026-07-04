package com.example.myapplication.data

import com.example.myapplication.data.model.SortItem

object Constants {

    const val UNSORTED = "Unsorted"

    val sortItems = listOf(
        SortItem("1", "Apple", "Fruit"),
        SortItem("2", "Broccoli", "Vegetable"),
        SortItem("3", "Mushroom", "Vegetable"),
        SortItem("4", "Banana", "Fruit"),
        SortItem("5", "Tomato", "Vegetable"),
        SortItem("6", "Orange", "Fruit"),
        SortItem("7", "Mango", "Fruit"),
        SortItem("8", "Pineapple", "Fruit"),
        SortItem("9", "Cucumber", "Vegetable"),
        SortItem("10", "Watermelon", "Fruit"),
        SortItem("11", "Carrot", "Vegetable"),
    )

    const val DUMMY_JSON_BASE_URL = "https://dummyjson.com/"
}
