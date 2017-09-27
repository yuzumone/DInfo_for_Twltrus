package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class Attraction(
        val name: String,
        val wait: String,
        val run: String,
        val fp: String,
        val url: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(wait)
        parcel.writeString(run)
        parcel.writeString(fp)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Attraction> {
        override fun createFromParcel(parcel: Parcel): Attraction {
            return Attraction(parcel)
        }

        override fun newArray(size: Int): Array<Attraction?> {
            return arrayOfNulls(size)
        }
    }
}