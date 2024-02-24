package com.example.mmm_mobile.models

class RecipeFilter(
    override var name: String? = null,
    override var sortBy: String? = "id",
    override var sortDirection: String? = "ASC",
    var servings: List<Int>? = null,
    var minKcalPerServing: Double? = null,
    var maxKcalPerServing: Double? = null
) : Filterable