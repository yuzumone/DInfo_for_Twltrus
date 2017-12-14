package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class Attraction(
        val name: String,
        val wait: String,
        val run: String,
        val fp: String,
        val update: String,
        val url: String) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(wait)
        writeString(run)
        writeString(fp)
        writeString(update)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Attraction> = object : Parcelable.Creator<Attraction> {
            override fun createFromParcel(source: Parcel): Attraction = Attraction(source)
            override fun newArray(size: Int): Array<Attraction?> = arrayOfNulls(size)
        }
    }
}