package me.chkfung.demo.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import me.chkfung.demo.MainAndroidCoroutineRule
import me.chkfung.demo.data.model.CurrencyInfo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class LocalDataSourceImplTest {
    lateinit var db: CurrencyDatabase
    lateinit var localDataSource: LocalDataSourceImpl

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), CurrencyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        localDataSource = LocalDataSourceImpl(db.currencyDao())
    }

    @Test
    fun insertRandomOrderCurrency_returnASCCurrency() = runBlocking {
        val insertData = arrayOf(
            CurrencyInfo(id = "BTC", name = "Bitcoin", symbol = "BTC"),
            CurrencyInfo(id = "XRP", name = "XRP", symbol = "XRP"),
            CurrencyInfo(id = "ETH", name = "Ethereum", symbol = "ETH"),
        )
        db.currencyDao().insertCurrency(*insertData)
        insertData.sortBy { it.name }
        assertThat(localDataSource.getCurrencyInfo().toTypedArray()).isEqualTo(insertData)
    }

    @After
    fun clear() {
        db.close()
    }
}