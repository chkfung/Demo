package me.chkfung.demo.data.source.local

import me.chkfung.demo.data.model.CurrencyInfo
import me.chkfung.demo.data.source.DataSource

class LocalDataSourceImpl(private val currencyDao: CurrencyDao) : DataSource {
    override fun getCurrencyInfo(): List<CurrencyInfo> {
        return currencyDao.getCurrency()
    }

}