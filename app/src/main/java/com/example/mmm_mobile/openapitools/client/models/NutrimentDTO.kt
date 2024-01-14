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
 * @param energyKcalPer100g 
 * @param fatPer100g 
 * @param fiberPer100g 
 * @param proteinsPer100g 
 * @param saltPer100g 
 * @param sodiumPer100g 
 * @param sugarsPer100g 
 */


data class NutrimentDTO (

    @Json(name = "id")
    val id: kotlin.Long,

    @Json(name = "energyKcalPer100g")
    val energyKcalPer100g: kotlin.Double? = null,

    @Json(name = "fatPer100g")
    val fatPer100g: kotlin.Double? = null,

    @Json(name = "fiberPer100g")
    val fiberPer100g: kotlin.Double? = null,

    @Json(name = "proteinsPer100g")
    val proteinsPer100g: kotlin.Double? = null,

    @Json(name = "saltPer100g")
    val saltPer100g: kotlin.Double? = null,

    @Json(name = "sodiumPer100g")
    val sodiumPer100g: kotlin.Double? = null,

    @Json(name = "sugarsPer100g")
    val sugarsPer100g: kotlin.Double? = null

)

