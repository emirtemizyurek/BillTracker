package org.example.billtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppSetting(
    @PrimaryKey val key: String,
    val value: String
)
