package at.aze.tradingtools.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class TradingDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
