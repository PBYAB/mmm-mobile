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

import org.openapitools.client.models.RecipeIngredientDTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param id 
 * @param ingredients 
 * @param instructions 
 * @param kcalPerServing 
 * @param name 
 * @param servings 
 */


data class RecipeDTO (

    @Json(name = "id")
    val id: kotlin.Long? = null,

    @Json(name = "ingredients")
    val ingredients: kotlin.collections.List<RecipeIngredientDTO>? = null,

    @Json(name = "instructions")
    val instructions: kotlin.String? = null,

    @Json(name = "kcalPerServing")
    val kcalPerServing: kotlin.Double? = null,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "servings")
    val servings: kotlin.Int? = null

)
