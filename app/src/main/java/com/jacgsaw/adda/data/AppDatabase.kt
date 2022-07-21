package com.jacgsaw.adda.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jacgsaw.adda.data.dao.BreedDao
import com.jacgsaw.adda.data.entity.Breed

@Database(entities = [Breed::class], version = 1)
@TypeConverters(CustomConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun breedDao(): BreedDao
}