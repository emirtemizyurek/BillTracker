package org.example.billtracker.data

import androidx.room.RoomDatabase



fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(androidx.sqlite.driver.bundled.BundledSQLiteDriver())
        .fallbackToDestructiveMigration(true)
        .build()
}
