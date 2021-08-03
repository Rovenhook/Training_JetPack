package com.rovenhook.training_jetpack.room

import androidx.room.*

@Entity
data class AnimalEntity(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String
) {
    constructor(name: String) : this(0, name)
}

@Dao
interface AnimalEntityDao {
    @Query("SELECT * FROM animalentity")
    suspend fun getAll(): List<AnimalEntity>

    @Insert
    suspend fun insert(animal: AnimalEntity)

    @Query("DELETE FROM animalentity")
    suspend fun deleteAll()
}

@Database(entities = arrayOf(AnimalEntity::class), version = 1)
abstract class AnimalEntitiesDataBase : RoomDatabase() {
    abstract fun animalEntityDao(): AnimalEntityDao
}