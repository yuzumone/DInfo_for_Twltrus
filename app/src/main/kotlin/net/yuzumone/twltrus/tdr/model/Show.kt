package net.yuzumone.twltrus.tdr.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class Show(
        val name: String,
        val time: String,
        val url: String) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelShow.CREATOR
    }
}