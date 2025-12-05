package org.example.billtracker

import androidx.compose.ui.window.ComposeUIViewController
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.billtracker.data.AppDatabase
import org.example.billtracker.di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

fun MainViewController() = ComposeUIViewController {
    App()
}

fun initKoin() {
    val databaseModule = module {
        single<AppDatabase> {
            val dbFilePath = NSHomeDirectory() + "/billtracker.db"
            Room.databaseBuilder<AppDatabase>(
                name = dbFilePath,
                factory = { AppDatabase::class.instantiateImpl() }
            )
            .setDriver(BundledSQLiteDriver())
            .build()
        }
    }

    startKoin {
        modules(appModule, databaseModule)
    }
}
