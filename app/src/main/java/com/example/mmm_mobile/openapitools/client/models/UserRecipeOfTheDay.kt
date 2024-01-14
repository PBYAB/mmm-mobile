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
import org.openapitools.client.models.User

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param drawnAt 
 * @param id 
 * @param recipe 
 * @param user 
 */


data class UserRecipeOfTheDay (

    @Json(name = "drawnAt")
    val drawnAt: java.time.OffsetDateTime? = null,

    @Json(name = "id")
    val id: kotlin.Long? = null,

    @Json(name = "recipe")
    val recipe: Recipe? = null,

    @Json(name = "user")
    val user: User? = null

)

