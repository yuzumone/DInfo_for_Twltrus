package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class Show(
        val name: String,
        val time: String,
        val update: String,
        val url: String) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(time)
        writeString(update)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Show> = object : Parcelable.Creator<Show> {
            override fun createFromParcel(source: Parcel): Show = Show(source)
            override fun newArray(size: Int): Array<Show?> = arrayOfNulls(size)
        }
    }
}