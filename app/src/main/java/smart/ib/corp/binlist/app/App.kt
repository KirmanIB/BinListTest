package smart.ib.corp.binlist.app

import android.app.Application

import androidx.room.Room
import smart.ib.corp.binlist.room.Database

class App:Application() {

    lateinit var db: Database
    override fun onCreate() {
        super.onCreate()
        // Инициализаия базы данных
        db = Room.databaseBuilder(applicationContext, Database::class.java, "bin_list_data_base").build()
    }

}