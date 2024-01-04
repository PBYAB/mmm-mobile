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

import org.openapitools.client.models.Recipe

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param comment 
 * @param id 
 * @param rating 
 * @param recipe 
 * @param userId 
 */


data class RecipeReview (

    @Json(name = "comment")
    val comment: kotlin.String? = null,

    @Json(name = "id")
    val id: kotlin.Long? = null,

    @Json(name = "rating")
    val rating: kotlin.Double? = null,

    @Json(name = "recipe")
    val recipe: Recipe? = null,

    @Json(name = "userId")
    val userId: kotlin.Long? = null

)
