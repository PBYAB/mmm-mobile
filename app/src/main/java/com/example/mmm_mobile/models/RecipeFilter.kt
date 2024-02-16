package com.example.mmm_mobile.models

class RecipeFilter(
    override var name: String?,
    override var sortBy: String?,
    override var sortDirection: String?,
    var servings: List<Int>? ,
    var minKcalPerServing: Double? ,
    var maxKcalPerServing: Double?,
) : Filterable