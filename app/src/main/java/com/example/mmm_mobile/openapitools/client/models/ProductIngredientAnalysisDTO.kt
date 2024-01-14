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


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param id 
 * @param fromPalmOil 
 * @param ingredientsDescription 
 * @param vegan 
 * @param vegetarian 
 */


data class ProductIngredientAnalysisDTO (

    @Json(name = "id")
    val id: kotlin.Long,

    @Json(name = "fromPalmOil")
    val fromPalmOil: kotlin.Boolean? = null,

    @Json(name = "ingredientsDescription")
    val ingredientsDescription: kotlin.String? = null,

    @Json(name = "vegan")
    val vegan: kotlin.Boolean? = null,

    @Json(name = "vegetarian")
    val vegetarian: kotlin.Boolean? = null

)

