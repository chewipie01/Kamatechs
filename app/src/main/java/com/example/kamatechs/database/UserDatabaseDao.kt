package com.example.kamatechs.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDatabaseDao {

    @Insert
    fun register(user: User)

    @Query("SELECT * FROM user_table WHERE username LIKE :username")
    fun login(username: String): User

}