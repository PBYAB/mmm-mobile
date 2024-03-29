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
 * @param id 
 * @param name 
 * @param users 
 */


data class Role (

    @Json(name = "id")
    val id: kotlin.Long? = null,

    @Json(name = "name")
    val name: Role.Name? = null,

    @Json(name = "users")
    val users: kotlin.collections.Set<User>? = null

) {

    /**
     * 
     *
     * Values: aDMIN,uSER,lIMITEDUSER
     */
    @JsonClass(generateAdapter = false)
    enum class Name(val value: kotlin.String) {
        @Json(name = "ADMIN") aDMIN("ADMIN"),
        @Json(name = "USER") uSER("USER"),
        @Json(name = "LIMITED_USER") lIMITEDUSER("LIMITED_USER");
    }
}

