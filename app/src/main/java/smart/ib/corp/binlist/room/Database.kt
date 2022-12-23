package smart.ib.corp.binlist.room

import androidx.room.RoomDatabase

//Привязка базы жанных с помощью RoomDatabase
@androidx.room.Database(entities = [BinList::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun binListDao():BinListDao
}
