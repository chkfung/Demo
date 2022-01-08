package me.chkfung.demo.data.source

import kotlinx.coroutines.flow.Flow
import me.chkfung.demo.data.model.CurrencyInfo

interface CurrencyRepo {
    fun getCurrency() : List<CurrencyInfo>
}