package net.yuzumone.twltrus.tdr.api

import net.yuzumone.twltrus.tdr.model.Restaurant
import org.jsoup.Jsoup
import org.jsoup.select.Elements

object RestaurantApi {

    private val randomLat = (Math.random() * 100).toInt()
    private val randomLng = (Math.random() * 100).toInt()
    private val tdlUrl = "http://info.tokyodisneyresort.jp/rt/s/gps/tdl_index.html" +
            "?nextUrl=http://info.tokyodisneyresort.jp/rt/s/realtime/tdl_restaurant.html" +
            "&lat=35.6329$randomLat&lng=139.8840$randomLng"
    private val tdsUrl = "http://info.tokyodisneyresort.jp/rt/s/gps/tds_index.html" +
            "?nextUrl=http://info.tokyodisneyresort.jp/rt/s/realtime/tds_restaurant.html" +
            "&lat=35.6329$randomLat&lng=139.8840$randomLng"

    fun getTdl(): List<Restaurant> {
        val doc = Jsoup.connect(tdlUrl).followRedirects(true).get()
        val list = doc.getElementsByClass("schedule").select("li")
        return analysis(list)
    }

    fun getTds(): List<Restaurant> {
        val doc = Jsoup.connect(tdsUrl).followRedirects(true).get()
        val list = doc.getElementsByClass("schedule").select("li")
        return analysis(list)
    }

    private fun analysis(list: Elements): List<Restaurant> {
        return list.map { restaurant ->
            val name = restaurant.select("h3").text()
            val waitTime = restaurant.select("p.waitTime").text()
            val run = restaurant.select("p.run").text()
            val left = restaurant.select("div.op-left").text()
            val right = restaurant.select("div.op-right").text()
            val url = restaurant.select("a").attr("href")
            Restaurant(name, waitTime, run, left, right, url)
        }
    }
}