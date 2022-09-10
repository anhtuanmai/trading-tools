package at.aze.tradingtools

import android.app.Application
import timber.log.Timber

class TradingApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}