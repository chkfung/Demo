package me.chkfung.demo.data.source

import me.chkfung.demo.data.model.CurrencyInfo

interface CurrencyRepo {
    fun getCurrency() : List<CurrencyInfo>
}