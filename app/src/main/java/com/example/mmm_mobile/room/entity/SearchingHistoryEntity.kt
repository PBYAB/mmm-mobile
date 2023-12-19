package com.example.mmm_mobile.room.entity

import androidx.room.Entity
import java.time.OffsetDateTime

@Entity(tableName = "searching_history")
data class SearchingHistoryEntity(
    val id: Long,
    val name: String,
    val date: OffsetDateTime,
    val userId: Long
)
