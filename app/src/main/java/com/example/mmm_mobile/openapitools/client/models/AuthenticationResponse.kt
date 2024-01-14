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
 * @param accessToken 
 * @param refreshToken 
 */


data class AuthenticationResponse (

    @Json(name = "accessToken")
    val accessToken: kotlin.String,

    @Json(name = "refreshToken")
    val refreshToken: kotlin.String? = null

)

