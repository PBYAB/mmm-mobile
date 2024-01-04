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

import org.openapitools.client.models.BrandDTO
import org.openapitools.client.models.PageableObject
import org.openapitools.client.models.SortObject

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param content 
 * @param empty 
 * @param first 
 * @param last 
 * @param number 
 * @param numberOfElements 
 * @param pageable 
 * @param propertySize 
 * @param sort 
 * @param totalElements 
 * @param totalPages 
 */


data class PageBrandDTO (

    @Json(name = "content")
    val content: kotlin.collections.List<BrandDTO>? = null,

    @Json(name = "empty")
    val empty: kotlin.Boolean? = null,

    @Json(name = "first")
    val first: kotlin.Boolean? = null,

    @Json(name = "last")
    val last: kotlin.Boolean? = null,

    @Json(name = "number")
    val number: kotlin.Int? = null,

    @Json(name = "numberOfElements")
    val numberOfElements: kotlin.Int? = null,

    @Json(name = "pageable")
    val pageable: PageableObject? = null,

    @Json(name = "size")
    val propertySize: kotlin.Int? = null,

    @Json(name = "sort")
    val sort: SortObject? = null,

    @Json(name = "totalElements")
    val totalElements: kotlin.Long? = null,

    @Json(name = "totalPages")
    val totalPages: kotlin.Int? = null

)
