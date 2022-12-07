package com.example.kamatechs.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "storage_table")
data class Storage (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "Temperature") val Temperature: Int?,
    @ColumnInfo(name = "Humidity") val Humidity: Int?,
    @ColumnInfo(name = "roll_no") val rollNo: Int?)
