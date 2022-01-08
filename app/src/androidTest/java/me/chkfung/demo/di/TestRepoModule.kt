package me.chkfung.demo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import me.chkfung.demo.data.source.CurrencyRepo
import me.chkfung.demo.data.source.FakeCurrencyRepo
import me.chkfung.demo.module.RepoModule
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepoModule::class]
)
abstract class TestRepoModule {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: FakeCurrencyRepo): CurrencyRepo
}