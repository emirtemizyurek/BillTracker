package org.example.billtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BillTemplate(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val defaultAmount: Double?,
    val paymentUrl: String?,
    val dayOfMonth: Int
)
