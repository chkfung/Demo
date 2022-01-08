package me.chkfung.demo.data.source.local

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.chkfung.demo.data.model.CurrencyInfo

@Database(entities = [CurrencyInfo::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {

        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getInstance(
            context: Context,
            scope: CoroutineScope
        ): CurrencyDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context, scope).also { INSTANCE = it }
        }

        private fun buildDatabase(
            context: Context,
            scope: CoroutineScope
        ) = Room.databaseBuilder(
            context.applicationContext,
            CurrencyDatabase::class.java, "currency.db"
        ).addCallback(object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                scope.launch(Dispatchers.IO) {
                    getInstance(context, scope).currencyDao().insertCurrency(*(PREPOPULATE_DATA))
                }
            }
        }).build()

        @VisibleForTesting
        private val PREPOPULATE_DATA = arrayOf(
            CurrencyInfo(id = "BTC", name = "Bitcoin", symbol = "BTC"),
            CurrencyInfo(id = "ETH", name = "Ethereum", symbol = "ETH"),
            CurrencyInfo(id = "XRP", name = "XRP", symbol = "XRP"),
            CurrencyInfo(id = "BCH", name = "Bitcoin Cash", symbol = "BCH"),
            CurrencyInfo(id = "LTC", name = "Litecoin", symbol = "LTC"),
            CurrencyInfo(id = "EOS", name = "EOS", symbol = "EOS"),
            CurrencyInfo(id = "BNB", name = "Binance Coin", symbol = "BNB"),
            CurrencyInfo(id = "LINK", name = "Chainlink", symbol = "LINK"),
            CurrencyInfo(id = "NEO", name = "NEO", symbol = "NEO"),
            CurrencyInfo(id = "ETC", name = "Ethereum Classic", symbol = "ETC"),
            CurrencyInfo(id = "ONT", name = "Ontology", symbol = "ONT"),
            CurrencyInfo(id = "CRO", name = "Crypto.com Chain", symbol = "CRO"),
            CurrencyInfo(id = "CUC", name = "Cucumber", symbol = "CUC"),
            CurrencyInfo(id = "USDC", name = "USD Coin", symbol = "USDC"),
        )
    }
}