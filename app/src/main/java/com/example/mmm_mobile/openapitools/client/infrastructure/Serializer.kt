package com.example.mmm_mobile.openapitools.client.infrastructure

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.openapitools.client.infrastructure.BigDecimalAdapter
import org.openapitools.client.infrastructure.BigIntegerAdapter
import org.openapitools.client.infrastructure.ByteArrayAdapter
import org.openapitools.client.infrastructure.LocalDateAdapter
import org.openapitools.client.infrastructure.LocalDateTimeAdapter
import org.openapitools.client.infrastructure.OffsetDateTimeAdapter
import org.openapitools.client.infrastructure.URIAdapter
import org.openapitools.client.infrastructure.UUIDAdapter

object Serializer {
    @JvmStatic
    val moshiBuilder: Moshi.Builder = Moshi.Builder()
        .add(OffsetDateTimeAdapter())
        .add(LocalDateTimeAdapter())
        .add(LocalDateAdapter())
        .add(UUIDAdapter())
        .add(ByteArrayAdapter())
        .add(URIAdapter())
        .add(KotlinJsonAdapterFactory())
        .add(BigDecimalAdapter())
        .add(BigIntegerAdapter())

    @JvmStatic
    val moshi: Moshi by lazy {
        moshiBuilder.build()
    }
}
