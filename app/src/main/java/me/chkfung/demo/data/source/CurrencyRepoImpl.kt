package me.chkfung.demo.data.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.chkfung.demo.data.model.CurrencyInfo

class CurrencyRepoImpl(private val localDataSource: DataSource) : CurrencyRepo {
    override fun getCurrency(): List<CurrencyInfo> {
        return localDataSource.getCurrencyInfo()
    }
}