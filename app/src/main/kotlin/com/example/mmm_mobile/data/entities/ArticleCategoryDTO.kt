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
 * @param id 
 * @param name 
 */
@Serializable
data class ArticleCategoryDTO (

    @SerialName(value = "id")
    val id: kotlin.Long? = null,

    @SerialName(value = "name")
    val name: kotlin.String? = null

)

