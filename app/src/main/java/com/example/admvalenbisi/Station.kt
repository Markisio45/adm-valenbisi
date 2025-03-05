package com.example.admvalenbisi

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

data class Station( val id: Int, val name: String, val availableBikes: Int, val freeSpaces: Int, val totalSpaces: Int): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "", // Handle null strings
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(availableBikes)
        parcel.writeInt(freeSpaces)
        parcel.writeInt(totalSpaces)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Station> {
        override fun createFromParcel(parcel: Parcel): Station {
            return Station(parcel)
        }

        override fun newArray(size: Int): Array<Station?> {
            return arrayOfNulls(size)
        }
    }
}