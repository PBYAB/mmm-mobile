package com.example.mmm_mobile.models


data class Recipe(

    override val id: Long,
    val name: String,
    val servings: Int?,
    val image: Any?,
    val time: Int?,
    val rating: Double?,
) : Identifiable

interface Identifiable {
    val id: Long
}