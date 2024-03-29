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

import org.openapitools.client.models.Product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param id 
 * @param product 
 * @param propertySize 
 * @param url 
 */


data class ProductImage (

    @Json(name = "id")
    val id: kotlin.Long? = null,

    @Json(name = "product")
    val product: Product? = null,

    @Json(name = "size")
    val propertySize: ProductImage.PropertySize? = null,

    @Json(name = "url")
    val url: kotlin.String? = null

) {

    /**
     * 
     *
     * Values: bIG,sMALL,tHUMBNAIL
     */
    @JsonClass(generateAdapter = false)
    enum class PropertySize(val value: kotlin.String) {
        @Json(name = "BIG") bIG("BIG"),
        @Json(name = "SMALL") sMALL("SMALL"),
        @Json(name = "THUMBNAIL") tHUMBNAIL("THUMBNAIL");
    }
}

