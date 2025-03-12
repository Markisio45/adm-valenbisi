package com.example.admvalenbisi

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReportDAO{
    @Query( "SELECT * FROM report")
    fun getAll(): Array<Report>

    @Query( "SELECT * FROM report WHERE station = :station")
    fun getByStation( station: Int): Array<Report>

    @Update
    fun update(report: Report)
}