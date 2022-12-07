package com.example.kamatechs.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StorageDao {
    @Query("SELECT * FROM storage_table")
    fun getAll(): List<Storage>

    @Query("SELECT * FROM storage_table WHERE roll_no LIKE :roll LIMIT 1")
    suspend fun findByRoll(roll: Int): Storage

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(storage: Storage)

    @Delete
    suspend fun delete(storage: Storage)

    @Query("DELETE FROM storage_table")
    suspend fun deleteAll()
}