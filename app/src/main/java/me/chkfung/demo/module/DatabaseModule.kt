package me.chkfung.demo.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import me.chkfung.demo.data.source.local.CurrencyDao
import me.chkfung.demo.data.source.local.CurrencyDatabase
import me.chkfung.demo.data.source.DataSource
import me.chkfung.demo.data.source.local.LocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideCurrencyDatabase(@ApplicationContext context: Context, @AppModule.AppScope scope: CoroutineScope): CurrencyDatabase {
        return CurrencyDatabase.getInstance(context, scope);
    }

    @Provides
    fun provideCurrencyDao(currencyDatabase: CurrencyDatabase): CurrencyDao {
        return currencyDatabase.currencyDao()
    }

    @Provides
    fun provideLocalDataSource(currencyDao: CurrencyDao) : DataSource {
        return LocalDataSourceImpl(currencyDao)
    }
}