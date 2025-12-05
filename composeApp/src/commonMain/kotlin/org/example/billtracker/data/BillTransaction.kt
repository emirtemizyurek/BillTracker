package org.example.billtracker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = BillTemplate::class,
            parentColumns = ["id"],
            childColumns = ["templateId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("templateId")]
)
data class BillTransaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val templateId: Long,
    val month: Int,
    val year: Int,
    val actualAmount: Double,
    val isPaid: Boolean,
    val paidDate: Long?
)
