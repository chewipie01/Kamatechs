package com.example.kamatechs.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StorageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // <- Annotate the 'addUser' function below. Set the onConflict strategy to IGNORE so if exactly the same user exists, it will just ignore it.
    suspend fun addStorage(storage: Storage)

    @Update
    suspend fun updateStorage(storage: Storage)

    @Delete
    suspend fun deleteStorage(storage: Storage)

    @Query("DELETE FROM storage_table")
    suspend fun deleteAllStorages()

    @Query("SELECT * from storage_table ORDER BY id ASC") // <- Add a query to fetch all users (in user_table) in ascending order by their IDs.
    fun readAllData(): LiveData<List<Storage>> // <- This means function return type is List. Specifically, a List of Users.

}