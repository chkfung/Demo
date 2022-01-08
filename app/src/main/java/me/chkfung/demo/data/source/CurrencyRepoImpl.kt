package me.chkfung.demo.data.source

import me.chkfung.demo.data.model.CurrencyInfo

class CurrencyRepoImpl(private val localDataSource: DataSource) : CurrencyRepo {
    override fun getCurrency(): List<CurrencyInfo> {
        return localDataSource.getCurrencyInfo()
    }
}