package com.example.paylater.data.di

import com.example.paylater.data.OfflinePayRepository
import com.example.paylater.data.PayRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPayRepository(
        offlinePayRepository: OfflinePayRepository
    ): PayRepository
}