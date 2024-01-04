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
 * @param fromPalmOil 
 * @param name 
 * @param vegan 
 * @param vegetarian 
 */


data class CreateProductIngredientRequest (

    @Json(name = "fromPalmOil")
    val fromPalmOil: kotlin.Boolean,

    @Json(name = "name")
    val name: kotlin.String,

    @Json(name = "vegan")
    val vegan: kotlin.Boolean,

    @Json(name = "vegetarian")
    val vegetarian: kotlin.Boolean

)
