package net.yuzumone.twltrus.tdr.api

import net.yuzumone.twltrus.tdr.model.Greeting
import org.jsoup.Jsoup
import org.jsoup.select.Elements

object GreetingApi {

    private val randomLat = (Math.random() * 100).toInt()
    private val randomLng = (Math.random() * 100).toInt()
    private val tdlUrl = "http://info.tokyodisneyresort.jp/rt/s/gps/tdl_index.html" +
            "?nextUrl=http://info.tokyodisneyresort.jp/rt/s/realtime/tdl_greeting.html" +
            "&lat=35.6329$randomLat&lng=139.8840$randomLng"
    private val tdsUrl = "http://info.tokyodisneyresort.jp/rt/s/gps/tds_index.html" +
            "?nextUrl=http://info.tokyodisneyresort.jp/rt/s/realtime/tds_greeting.html" +
            "&lat=35.6329$randomLat&lng=139.8840$randomLng"

    fun getTdl(): List<Greeting> {
        val doc = Jsoup.connect(tdlUrl).followRedirects(true).get()
        val list = doc.select("#greeting > section > article.greeting > li")
        return analysis(list)
    }

    fun getTds(): List<Greeting> {
        val doc = Jsoup.connect(tdsUrl).followRedirects(true).get()
        val list = doc.select("#greeting > section > article.greeting > li")
        return analysis(list)
    }

    private fun analysis(list: Elements): List<Greeting> {
        return list.map { greeting ->
            val name = greeting.select("h3").text()
            val left = greeting.select("div.op-left").eachText().joinToString("\n")
            val right = greeting.select("div.op-right").eachText().joinToString("\n")
            val wait = greeting.select("p.waitTime").text()
            val update = greeting.select("p.update").text()
                    .replace("^\\(|\\)\$".toRegex(), "")
            val url = greeting.select("a").attr("href")
            Greeting(name, left, right, wait, update, url)
        }
    }
}