package com.example.admvalenbisi

import android.content.Context
import android.os.Parcelable
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
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "station") var station: Int?,
    @ColumnInfo(name = "status") var status: ReportStatus?,
    @ColumnInfo(name = "type") var type: ReportType?
) : Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        ReportStatus.fromString(parcel.readString() ?: ReportStatus.OPEN.status),
        ReportType.fromString(parcel.readString() ?: ReportType.MECHANICAL.type)
    )

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeValue(station)
        parcel.writeString(status?.status)
        parcel.writeString(type?.type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Report> {
        override fun createFromParcel(parcel: android.os.Parcel): Report {
            return Report(parcel)
        }

        override fun newArray(size: Int): Array<Report?> {
            return arrayOfNulls(size)
        }
    }
}

@Database( entities = [Report::class], version = 5, exportSchema = false)
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