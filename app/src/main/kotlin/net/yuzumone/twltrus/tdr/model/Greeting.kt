package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class Greeting(
        val name: String,
        val left: String,
        val right: String,
        val wait: String,
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
        parcel.writeString(left)
        parcel.writeString(right)
        parcel.writeString(wait)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Greeting> {
        override fun createFromParcel(parcel: Parcel): Greeting {
            return Greeting(parcel)
        }

        override fun newArray(size: Int): Array<Greeting?> {
            return arrayOfNulls(size)
        }
    }
}