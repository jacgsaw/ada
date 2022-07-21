package com.jacgsaw.adda.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Breed(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val name: String,
    val imageUrl: String?,
    val variations: List<String> )