package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class Restaurant(
        val name: String,
        val wait: String,
        val run: String,
        val left: String,
        val right: String,
        val update: String,
        val url: String) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
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
        writeString(left)
        writeString(right)
        writeString(update)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Restaurant> = object : Parcelable.Creator<Restaurant> {
            override fun createFromParcel(source: Parcel): Restaurant = Restaurant(source)
            override fun newArray(size: Int): Array<Restaurant?> = arrayOfNulls(size)
        }
    }
}