import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.billtracker.data.AppDatabase
import org.example.billtracker.di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.io.File
import org.example.billtracker.App

fun main() = application {
    val dbFile = File(System.getProperty("user.home"), "billtracker.db")
    
    val databaseModule = module {
        single<AppDatabase> {
            Room.databaseBuilder<AppDatabase>(
                name = dbFile.absolutePath,
            )
            .setDriver(BundledSQLiteDriver())
            .build()
        }
    }

    startKoin {
        modules(appModule, databaseModule)
    }

    Window(onCloseRequest = ::exitApplication, title = "Bill Tracker") {
        App()
    }
}
