/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.openapitools.client.models

import org.openapitools.client.models.Allergen
import org.openapitools.client.models.Brand
import org.openapitools.client.models.Category
import org.openapitools.client.models.Country
import org.openapitools.client.models.Nutriment
import org.openapitools.client.models.ProductImage
import org.openapitools.client.models.ProductIngredient
import org.openapitools.client.models.ProductIngredientAnalysis

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param allergens 
 * @param barcode 
 * @param brands 
 * @param categories 
 * @param countries 
 * @param id 
 * @param images 
 * @param ingredientAnalysis 
 * @param ingredients 
 * @param name 
 * @param novaGroup 
 * @param nutriScore 
 * @param nutriment 
 * @param quantity 
 */


data class Product (

    @Json(name = "allergens")
    val allergens: kotlin.collections.Set<Allergen>? = null,

    @Json(name = "barcode")
    val barcode: kotlin.String? = null,

    @Json(name = "brands")
    val brands: kotlin.collections.Set<Brand>? = null,

    @Json(name = "categories")
    val categories: kotlin.collections.Set<Category>? = null,

    @Json(name = "countries")
    val countries: kotlin.collections.Set<Country>? = null,

    @Json(name = "id")
    val id: kotlin.Long? = null,

    @Json(name = "images")
    val images: kotlin.collections.Set<ProductImage>? = null,

    @Json(name = "ingredientAnalysis")
    val ingredientAnalysis: ProductIngredientAnalysis? = null,

    @Json(name = "ingredients")
    val ingredients: kotlin.collections.Set<ProductIngredient>? = null,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "novaGroup")
    val novaGroup: kotlin.Int? = null,

    @Json(name = "nutriScore")
    val nutriScore: kotlin.Int? = null,

    @Json(name = "nutriment")
    val nutriment: Nutriment? = null,

    @Json(name = "quantity")
    val quantity: kotlin.String? = null

)
