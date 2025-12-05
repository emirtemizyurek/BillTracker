package org.example.billtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.billtracker.data.AppDatabase
import org.example.billtracker.di.appModule
import org.example.billtracker.utils.AndroidContextHolder
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidContextHolder.activity = this

        val databaseModule = module {
            single<AppDatabase> {
                val context = androidContext()
                val dbFile = context.getDatabasePath("billtracker.db")
                Room.databaseBuilder<AppDatabase>(
                    context = context,
                    name = dbFile.absolutePath
                )
                .setDriver(BundledSQLiteDriver())
                .build()
            }
        }

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule, databaseModule)
        }

        setContent {
            App()
        }
    }
}
