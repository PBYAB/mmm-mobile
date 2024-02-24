package com.example.mmm_mobile.models

class ProductFilter(
    override var name: String? = null,
    override var sortBy: String? = "id",
    override var sortDirection: String? = "ASC",
    var quantity: String? = null,
    var nutriScore: List<Int>? = null,
    var novaGroups: List<Int>? = null,
    var category: List<Long>? = null,
    var allergens: List<Long>? = null,
    var country: List<Long>? = null
) : Filterable