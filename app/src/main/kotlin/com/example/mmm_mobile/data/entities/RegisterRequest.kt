/**
 * OpenApi specification - MMM
 *
 * OpenApi documentation for MMM
 *
 * The version of the OpenAPI document: 1.0
 * Contact: contact@mmm.com
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.example.mmm_mobile.data.entities


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param email 
 * @param firstName 
 * @param lastName 
 * @param password 
 */
@Serializable
data class RegisterRequest (

    @SerialName(value = "email")
    val email: kotlin.String,

    @SerialName(value = "firstName")
    val firstName: kotlin.String,

    @SerialName(value = "lastName")
    val lastName: kotlin.String,

    @SerialName(value = "password")
    val password: kotlin.String

)
