package net.yuzumone.twltrus.tdr.api

import net.yuzumone.twltrus.tdr.model.Show
import org.jsoup.Jsoup
import org.jsoup.select.Elements

object ShowApi {

    private val randomLat = (Math.random() * 100).toInt()
    private val randomLng = (Math.random() * 100).toInt()
    private val tdlUrl = "https://info.tokyodisneyresort.jp/rt/s/gps/tdl_index.html" +
            "?nextUrl=http://info.tokyodisneyresort.jp/rt/s/realtime/tdl_show.html" +
            "&lat=35.6329$randomLat&lng=139.8840$randomLng"
    private val tdsUrl = "http://info.tokyodisneyresort.jp/rt/s/gps/tds_index.html" +
            "?nextUrl=http://info.tokyodisneyresort.jp/rt/s/realtime/tds_show.html" +
            "&lat=35.6329$randomLat&lng=139.8840$randomLng"

    fun getTdl(): List<Show> {
        val doc = Jsoup.connect(tdlUrl).followRedirects(true).get()
        val list = doc.select("#show > article > li")
        return analysis(list)
    }

    fun getTds(): List<Show> {
        val doc = Jsoup.connect(tdsUrl).followRedirects(true).get()
        val list = doc.select("#show > article > li")
        return analysis(list)
    }

    private fun analysis(list: Elements): List<Show> {
        return list.map { show ->
            val name = show.select("h3").text()
            val time = show.select("p.time").eachText().joinToString("\n")
            val url = show.select("a").attr("href")
            Show(name, time, url)
        }
    }
}