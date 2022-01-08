package me.chkfung.demo.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.chkfung.demo.data.source.CurrencyRepo
import me.chkfung.demo.data.source.CurrencyRepoImpl
import me.chkfung.demo.data.source.DataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideCurrencyRepo(localDataSource: DataSource): CurrencyRepo {
        return CurrencyRepoImpl(localDataSource)
    }
}