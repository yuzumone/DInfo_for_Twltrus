package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class Rehab(
        val name: String,
        val date: String,
        val url: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(date)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rehab> {
        override fun createFromParcel(parcel: Parcel): Rehab {
            return Rehab(parcel)
        }

        override fun newArray(size: Int): Array<Rehab?> {
            return arrayOfNulls(size)
        }
    }
}