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
 * @param categoryId 
 * @param content 
 * @param status 
 * @param title 
 */


data class CreateArticleRequest (

    @Json(name = "categoryId")
    val categoryId: kotlin.Long? = null,

    @Json(name = "content")
    val content: kotlin.String? = null,

    @Json(name = "status")
    val status: CreateArticleRequest.Status? = null,

    @Json(name = "title")
    val title: kotlin.String? = null

) {

    /**
     * 
     *
     * Values: pUBLISHED,dRAFT
     */
    @JsonClass(generateAdapter = false)
    enum class Status(val value: kotlin.String) {
        @Json(name = "PUBLISHED") pUBLISHED("PUBLISHED"),
        @Json(name = "DRAFT") dRAFT("DRAFT");
    }
}
