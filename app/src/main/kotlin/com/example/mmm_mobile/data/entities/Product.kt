/**
 * OpenApi specification - MMM
 *
 * OpenApi documentation for MMM
 *
 * The version of the OpenAPI document: 1.0
 * Contact: contact@mmm.com
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.example.mmm_mobile.data.entities

import com.example.mmm_mobile.data.entities.Allergen
import com.example.mmm_mobile.data.entities.Brand
import com.example.mmm_mobile.data.entities.Category
import com.example.mmm_mobile.data.entities.Country
import com.example.mmm_mobile.data.entities.Nutriment
import com.example.mmm_mobile.data.entities.ProductIngredient
import com.example.mmm_mobile.data.entities.ProductIngredientAnalysis

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param allergens 
 * @param barcode 
 * @param brands 
 * @param categories 
 * @param countries 
 * @param id 
 * @param ingredientAnalysis 
 * @param ingredients 
 * @param name 
 * @param novaGroup 
 * @param nutriScore 
 * @param nutriment 
 * @param quantity 
 */
@Serializable
data class Product (

    @SerialName(value = "allergens")
    val allergens: kotlin.collections.Set<Allergen>? = null,

    @SerialName(value = "barcode")
    val barcode: kotlin.String? = null,

    @SerialName(value = "brands")
    val brands: kotlin.collections.Set<Brand>? = null,

    @SerialName(value = "categories")
    val categories: kotlin.collections.Set<Category>? = null,

    @SerialName(value = "countries")
    val countries: kotlin.collections.Set<Country>? = null,

    @SerialName(value = "id")
    val id: kotlin.Long? = null,

    @SerialName(value = "ingredientAnalysis")
    val ingredientAnalysis: ProductIngredientAnalysis? = null,

    @SerialName(value = "ingredients")
    val ingredients: kotlin.collections.Set<ProductIngredient>? = null,

    @SerialName(value = "name")
    val name: kotlin.String? = null,

    @SerialName(value = "novaGroup")
    val novaGroup: kotlin.Int? = null,

    @SerialName(value = "nutriScore")
    val nutriScore: kotlin.Int? = null,

    @SerialName(value = "nutriment")
    val nutriment: Nutriment? = null,

    @SerialName(value = "quantity")
    val quantity: kotlin.String? = null

)

