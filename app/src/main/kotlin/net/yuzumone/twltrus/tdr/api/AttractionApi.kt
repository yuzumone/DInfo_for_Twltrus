package net.yuzumone.twltrus.tdr.api

import net.yuzumone.twltrus.tdr.model.Attraction
import org.jsoup.Jsoup
import org.jsoup.select.Elements

object AttractionApi {

    private val randomLat = (Math.random() * 100).toInt()
    private val randomLng = (Math.random() * 100).toInt()
    private val tdlUrl = "http://info.tokyodisneyresort.jp/rt/s/gps/tdl_index.html" +
            "?nextUrl=http://info.tokyodisneyresort.jp/rt/s/realtime/tdl_attraction.html" +
            "&lat=35.6329$randomLat&lng=139.8840$randomLng"
    private val tdsUrl = "http://info.tokyodisneyresort.jp/rt/s/gps/tds_index.html" +
            "?nextUrl=http://info.tokyodisneyresort.jp/rt/s/realtime/tds_attraction.html" +
            "&lat=35.6329$randomLat&lng=139.8840$randomLng"

    fun getTdl(): List<Attraction> {
        val doc = Jsoup.connect(tdlUrl).followRedirects(true).get()
        val list = doc.getElementsByClass("schedule").select("li")
        return analysis(list)
    }

    fun getTds(): List<Attraction> {
        val doc = Jsoup.connect(tdsUrl).followRedirects(true).get()
        val list = doc.getElementsByClass("schedule").select("li")
        return analysis(list)
    }

    private fun analysis(list: Elements): List<Attraction> {
        return list.map { attraction ->
            val name = attraction.select("h3").text()
            val waitTime = attraction.select("p.waitTime").text()
            val runTime = attraction.select("p.run").text()
            val fp = attraction.select("p.fp").text()
            val update = attraction.select("p.update").text()
                    .replace("^\\(|\\)\$".toRegex(), "")
            val url = attraction.select("a").attr("href")
            Attraction(name, waitTime, runTime, fp, update, url)
        }
    }
}