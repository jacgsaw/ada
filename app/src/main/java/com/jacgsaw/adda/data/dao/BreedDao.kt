package com.jacgsaw.adda.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jacgsaw.adda.data.entity.Breed

@Dao
interface BreedDao {

    @Query("SELECT * FROM Breed")
    fun getAll(): List<Breed>

    @Insert
    fun insertAll(vararg breed: Breed)

    @Delete
    fun delete(breed: Breed)
}