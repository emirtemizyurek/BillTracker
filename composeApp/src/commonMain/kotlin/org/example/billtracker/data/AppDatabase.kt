package org.example.billtracker.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BillTemplate::class, BillTransaction::class, AppSetting::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun billDao(): BillDao
}
