package net.yuzumone.twltrus.tdr.data.api

import net.yuzumone.twltrus.tdr.utils.cookieHeader
import org.jsoup.Connection
import org.jsoup.Jsoup

object CookieApi {

    private const val cookieUrl = "https://www.tokyodisneyresort.jp/view_interface.php" +
            "?blockId=94199&pageBlockId=13476&nextUrl=tdlattraction"
    private val lat = (Math.random() * 100).toInt()
    private val lng = (Math.random() * 100).toInt()

    fun getCookie(): String {
        val res = Jsoup.connect(cookieUrl).cookieHeader()
                .ignoreContentType(true)
                .data("lat", "35.6330$lat", "lon", "139.8840$lng")
                .method(Connection.Method.POST)
                .execute()
        return res.cookie("tdrloc")
    }
}