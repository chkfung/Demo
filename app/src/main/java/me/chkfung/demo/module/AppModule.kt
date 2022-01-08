package me.chkfung.demo.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AppScope

    @Singleton
    @AppScope
    @Provides
    fun provideApplicationScope(@ApplicationContext context: Context): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }
}