package me.chkfung.demo.data.source

import me.chkfung.demo.data.model.CurrencyInfo

interface DataSource {
    fun getCurrencyInfo() : List<CurrencyInfo>
}