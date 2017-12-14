package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class Rehab(
        val name: String,
        val date: String,
        val url: String) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(date)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Rehab> = object : Parcelable.Creator<Rehab> {
            override fun createFromParcel(source: Parcel): Rehab = Rehab(source)
            override fun newArray(size: Int): Array<Rehab?> = arrayOfNulls(size)
        }
    }
}