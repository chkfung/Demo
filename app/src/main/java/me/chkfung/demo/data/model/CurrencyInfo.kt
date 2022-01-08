package me.chkfung.demo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyInfo(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String
)