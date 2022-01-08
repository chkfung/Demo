package me.chkfung.demo.data.source.local

import me.chkfung.demo.data.model.CurrencyInfo
import me.chkfung.demo.data.source.DataSource

class FakeLocalDataSource : DataSource {
    var emptyData: Boolean = false
    var dataSourceFailure: Boolean = false

    override fun getCurrencyInfo(): List<CurrencyInfo> {
        if (dataSourceFailure)
            throw Exception("DB read failure")
        if (emptyData)
            return emptyList()
        else
            return PREPOPULATE_DATA
    }


    companion object {
        private val PREPOPULATE_DATA = listOf(
            CurrencyInfo(id = "BTC", name = "Bitcoin", symbol = "BTC"),
            CurrencyInfo(id = "CUC", name = "Cucumber", symbol = "CUC"),
            CurrencyInfo(id = "USDC", name = "USD Coin", symbol = "USDC"),
        )
    }
}