package org.example.billtracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Query("SELECT * FROM BillTemplate")
    fun getAllTemplates(): Flow<List<BillTemplate>>

    @Query("SELECT * FROM BillTemplate")
    suspend fun getAllTemplatesOneShot(): List<BillTemplate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: BillTemplate): Long

    @Update
    suspend fun updateTemplate(template: BillTemplate)

    @Delete
    suspend fun deleteTemplate(template: BillTemplate)

    @Query("SELECT * FROM BillTransaction WHERE month = :month AND year = :year")
    fun getTransactionsForMonth(month: Int, year: Int): Flow<List<BillTransaction>>

    @Query("SELECT * FROM BillTransaction WHERE month = :month AND year = :year")
    suspend fun getTransactionsForMonthOneShot(month: Int, year: Int): List<BillTransaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: BillTransaction): Long
    
    @Update
    suspend fun updateTransaction(transaction: BillTransaction)

    @Query("SELECT value FROM AppSetting WHERE key = :key")
    suspend fun getSetting(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSetting(setting: AppSetting)
}
