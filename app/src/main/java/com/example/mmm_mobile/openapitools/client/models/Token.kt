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

import org.openapitools.client.models.User

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param expired 
 * @param id 
 * @param revoked 
 * @param token 
 * @param tokenType 
 * @param user 
 */


data class Token (

    @Json(name = "expired")
    val expired: kotlin.Boolean? = null,

    @Json(name = "id")
    val id: kotlin.Int? = null,

    @Json(name = "revoked")
    val revoked: kotlin.Boolean? = null,

    @Json(name = "token")
    val token: kotlin.String? = null,

    @Json(name = "tokenType")
    val tokenType: Token.TokenType? = null,

    @Json(name = "user")
    val user: User? = null

) {

    /**
     * 
     *
     * Values: bEARER
     */
    @JsonClass(generateAdapter = false)
    enum class TokenType(val value: kotlin.String) {
        @Json(name = "BEARER") bEARER("BEARER");
    }
}

