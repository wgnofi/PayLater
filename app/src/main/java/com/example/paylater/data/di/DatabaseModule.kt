package com.example.paylater.data.di

import android.content.Context
import androidx.room.Room
import com.example.paylater.data.PayDao
import com.example.paylater.data.PayDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePayDatabase(@ApplicationContext context: Context): PayDatabase {
        return Room.databaseBuilder(
                context,
                PayDatabase::class.java,
                "pay_database"
            ).fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun providePayDao(database: PayDatabase): PayDao {
        return database.payDao()
    }
}