package net.yuzumone.twltrus.tdr.api

import net.yuzumone.twltrus.tdr.extension.regularHeader
import net.yuzumone.twltrus.tdr.model.Show
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.util.*

object ShowApi {

    private val randomLat = (Math.random() * 100).toInt()
    private val randomLng = (Math.random() * 100).toInt()
    private val tdlUrl = "http://info.tokyodisneyresort.jp/rt/s/gps/tdl_index.html" +
            "?nextUrl=http://info.tokyodisneyresort.jp/rt/s/realtime/tdl_show.html" +
            "&lat=35.6329$randomLat&lng=139.8840$randomLng"
    private val tdsUrl = "http://info.tokyodisneyresort.jp/rt/s/gps/tds_index.html" +
            "?nextUrl=http://info.tokyodisneyresort.jp/rt/s/realtime/tds_show.html" +
            "&lat=35.6329$randomLat&lng=139.8840$randomLng"

    fun getTdl(date: Date): List<Show> {
        val format = SimpleDateFormat("yyyyMMdd").format(date)
        val url = "http://www.tokyodisneyresort.jp/tdl/daily/show/$format/"
        val doc = Jsoup.connect(url).followRedirects(true).regularHeader().get()
        val list = doc.select("div.section-list > div > ul > li")
        return analysis(list)
    }

    fun getTds(date: Date): List<Show> {
        val format = SimpleDateFormat("yyyyMMdd").format(date)
        val url = "http://www.tokyodisneyresort.jp/tds/daily/show/$format/"
        val doc = Jsoup.connect(url).followRedirects(true).regularHeader().get()
        val list = doc.select("div.section-list > div > ul > li")
        return analysis(list)
    }

    fun getRealtimeTdl(): List<Show> {
        val doc = Jsoup.connect(tdlUrl).followRedirects(true).regularHeader().get()
        val list = doc.select("#show > article > li")
        return analysis(list)
    }

    fun getRealtimeTds(): List<Show> {
        val doc = Jsoup.connect(tdsUrl).followRedirects(true).regularHeader().get()
        val list = doc.select("#show > article > li")
        return analysis(list)
    }

    private fun analysis(list: Elements): List<Show> {
        return list.map { show ->
            val name = show.select("h3").text()
            val time = show.select("div.timeTable").eachText().joinToString("\n")
            val update = show.select("p.update").text()
                    .replace("^\\(|\\)\$".toRegex(), "")
            val url = show.select("a").attr("href")
            val link = "http://www.tokyodisneyresort.jp$url"
            Show(name, time, update, link)
        }
    }
}