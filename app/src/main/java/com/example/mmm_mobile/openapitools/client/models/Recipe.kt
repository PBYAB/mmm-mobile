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

import org.openapitools.client.models.RecipeIngredient
import org.openapitools.client.models.RecipeReview

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param averageRating 
 * @param coverImageUrl 
 * @param id 
 * @param ingredients 
 * @param instructions 
 * @param kcalPerServing 
 * @param name 
 * @param published 
 * @param reviews 
 * @param servings 
 * @param totalTime 
 */


data class Recipe (

    @Json(name = "averageRating")
    val averageRating: kotlin.Double? = null,

    @Json(name = "coverImageUrl")
    val coverImageUrl: kotlin.String? = null,

    @Json(name = "id")
    val id: kotlin.Long? = null,

    @Json(name = "ingredients")
    val ingredients: kotlin.collections.Set<RecipeIngredient>? = null,

    @Json(name = "instructions")
    val instructions: kotlin.String? = null,

    @Json(name = "kcalPerServing")
    val kcalPerServing: kotlin.Double? = null,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "published")
    val published: kotlin.Boolean? = null,

    @Json(name = "reviews")
    val reviews: kotlin.collections.Set<RecipeReview>? = null,

    @Json(name = "servings")
    val servings: kotlin.Int? = null,

    @Json(name = "totalTime")
    val totalTime: kotlin.Int? = null

)

