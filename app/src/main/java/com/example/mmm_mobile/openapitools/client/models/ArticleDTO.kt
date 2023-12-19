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

import org.openapitools.client.models.ArticleCategoryDTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param category 
 * @param content 
 * @param createdAt 
 * @param id 
 * @param status 
 * @param title 
 */


data class ArticleDTO (

    @Json(name = "category")
    val category: ArticleCategoryDTO? = null,

    @Json(name = "content")
    val content: kotlin.String? = null,

    @Json(name = "createdAt")
    val createdAt: java.time.OffsetDateTime? = null,

    @Json(name = "id")
    val id: kotlin.Long? = null,

    @Json(name = "status")
    val status: ArticleDTO.Status? = null,

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

