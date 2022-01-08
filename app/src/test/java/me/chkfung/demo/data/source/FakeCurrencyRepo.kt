package me.chkfung.demo.data.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.chkfung.demo.data.model.CurrencyInfo
import java.lang.Exception

class FakeCurrencyRepo : CurrencyRepo {
    var shouldReturnError: Boolean = false
    var shouldReturnEmpty: Boolean = false

    override fun getCurrency(): List<CurrencyInfo> {
        if (shouldReturnError)
            throw Exception()
        if (shouldReturnEmpty)
            return emptyList()
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