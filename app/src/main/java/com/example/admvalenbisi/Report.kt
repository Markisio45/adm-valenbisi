package com.example.admvalenbisi

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

enum class ReportStatus(val status: String) {
    OPEN("Open"),
    PROCESSING("Processing"),
    CLOSED("Closed");

    companion object {
        fun fromString(value: String): ReportStatus {
            return values().find { it.status == value } ?: OPEN // Valor por defecto
        }
    }
}
class Converters {
    @TypeConverter
    fun fromStatus(status: ReportStatus): String {
        return status.status
    }

    @TypeConverter
    fun toStatus(status: String): ReportStatus {
        return ReportStatus.fromString(status)
    }

    @TypeConverter
    fun fromType(type: ReportType): String {
        return type.type
    }

    @TypeConverter
    fun toType(type: String): ReportType {
        return ReportType.fromString(type)
    }
}

enum class ReportType(val type: String) {
    MECHANICAL("Mechanical"),
    ELECTRIC("Electric"),
    PAINTING("Painting"),
    MASONRY("Masonry");

    companion object {
        fun fromString(value: String): ReportType {
            return values().find { it.type == value } ?: MECHANICAL // Valor por defecto
        }
    }
}

@Entity(tableName = "report")
data class Report(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "station") val station: Int?,
    @ColumnInfo(name = "status") val status: ReportStatus?,
    @ColumnInfo(name = "type") val type: ReportType?
)

@Database( entities = [Report::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ReportDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDAO

    companion object {
        @Volatile
        private var INSTANCE: ReportDatabase? = null

        suspend fun getInstance(context: Context): ReportDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReportDatabase::class.java,
                    "report_database" // Nombre de la BD
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}