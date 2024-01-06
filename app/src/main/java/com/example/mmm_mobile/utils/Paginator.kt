package com.example.mmm_mobile.utils

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}