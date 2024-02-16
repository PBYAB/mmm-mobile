package com.example.mmm_mobile.models

class ProductFilter(
    override var name: String?,
    override var sortBy: String?,
    override var sortDirection: String?,
    var quantity: String?,
    var nutriScore: List<Int>?,
    var novaGroups: List<Int>?,
    var category: List<Long>?,
    var allergens: List<Long>?,
    var country: List<Long>?,
) : Filterable