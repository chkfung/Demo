package me.chkfung.demo.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.chkfung.demo.data.model.CurrencyInfo

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency ORDER BY name ASC")
    fun getCurrency() : List<CurrencyInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(vararg currency: CurrencyInfo)
}