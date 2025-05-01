package com.example.admvalenbisi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReportDAO{
    @Query( "SELECT * FROM report")
    suspend fun getAll(): List<Report>

    @Query( "SELECT * FROM report WHERE station = :station")
    suspend fun getByStation( station: Int): List<Report>

    @Update
    suspend fun update(report: Report)

    @Insert
    suspend fun insert(report: Report)
}