package at.aze.tradingtools

import android.app.Application
import android.content.Context
import androidx.room.Room
import at.aze.tradingtools.persistence.TradingDatabase
import timber.log.Timber

class TradingApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

    companion object {

        private var _db: TradingDatabase? = null

        private val lock = Unit

        fun getDatabase(context: Context): TradingDatabase {
            synchronized(lock) {
                if (_db == null) {
                    _db = Room.databaseBuilder<TradingDatabase>(
                        context.applicationContext,
                        TradingDatabase::class.java, "TRADING_DATABASE"
                    )
                        .fallbackToDestructiveMigration() // data will be erased if migration fails
                        .build()
                }
                return _db!!
            }
        }
    }
}