package net.yuzumone.twltrus.tdr.api

import net.yuzumone.twltrus.tdr.model.Show
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.util.*

object ShowApi {

    val date: String by lazy {
        SimpleDateFormat("yyyyMMdd").format(Date())
    }
    val tdlUrl = "http://info.tokyodisneyresort.jp/s/daily_schedule/show/tdl_$date.html"
    val tdsUrl = "http://info.tokyodisneyresort.jp/s/daily_schedule/show/tds_$date.html"

    fun getTdl(): List<Show> {
        val doc = Jsoup.connect(tdlUrl).followRedirects(true).get()
        val list = doc.select("#greeting > li")
        return analysis(list)
    }

    fun getTds(): List<Show> {
        val doc = Jsoup.connect(tdsUrl).followRedirects(true).get()
        val list = doc.select("#greeting > li")
        return analysis(list)
    }

    private fun analysis(list: Elements): List<Show> {
        return list.map { show ->
            val name = show.select("h3").text()
            val time = show.select("p.time").text()
            val url = show.select("a").attr("href")
            Show(name, time, url)
        }
    }
}