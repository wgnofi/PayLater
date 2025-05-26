package com.example.paylater.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Payment::class], version = 1, exportSchema = false)
abstract class PayDatabase: RoomDatabase() {
    abstract fun payDao(): PayDao
}