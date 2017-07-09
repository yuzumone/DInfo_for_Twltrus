package net.yuzumone.twltrus.tdr.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class Attraction(
        val name: String,
        val wait: String,
        val run: String,
        val fp: String,
        val url: String) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelAttraction.CREATOR
    }
}