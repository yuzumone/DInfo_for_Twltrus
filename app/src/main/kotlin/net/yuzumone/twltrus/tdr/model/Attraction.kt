package net.yuzumone.twltrus.tdr.model

import android.os.Parcel
import android.os.Parcelable
import net.yuzumone.twltrus.tdr.moshi.StandbyTime

data class Attraction(
        val FacilityName: String,
        val FacilityURLSP: String?,
        @StandbyTime val StandbyTime: String?,
        val UpdateTime: String,
        val FsStatus: String?,
        val OperatingStatus: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(FacilityName)
        parcel.writeString(FacilityURLSP)
        parcel.writeString(StandbyTime)
        parcel.writeString(UpdateTime)
        parcel.writeString(FsStatus)
        parcel.writeString(OperatingStatus)
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