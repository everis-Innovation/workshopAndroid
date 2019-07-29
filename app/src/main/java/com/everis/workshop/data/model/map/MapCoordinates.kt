package com.everis.workshop.data.model.map

import android.os.Parcel
import android.os.Parcelable

class MapCoordinates() : Parcelable{

    var latitude = 0.0
    var longitude = 0.0

    constructor(parcel: Parcel) : this() {
        latitude = parcel.readDouble()
        longitude = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MapCoordinates> {
        override fun createFromParcel(parcel: Parcel): MapCoordinates {
            return MapCoordinates(parcel)
        }

        override fun newArray(size: Int): Array<MapCoordinates?> {
            return arrayOfNulls(size)
        }
    }
}