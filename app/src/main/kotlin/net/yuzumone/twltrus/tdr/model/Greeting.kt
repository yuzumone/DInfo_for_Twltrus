package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable

data class Greeting(
        val name: String,
        val left: String,
        val right: String,
        val wait: String,
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
        writeString(left)
        writeString(right)
        writeString(wait)
        writeString(update)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Greeting> = object : Parcelable.Creator<Greeting> {
            override fun createFromParcel(source: Parcel): Greeting = Greeting(source)
            override fun newArray(size: Int): Array<Greeting?> = arrayOfNulls(size)
        }
    }
}