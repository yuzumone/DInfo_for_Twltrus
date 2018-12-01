package net.yuzumone.twltrus.tdr.data.api

import net.yuzumone.twltrus.tdr.utils.regularHeader
import net.yuzumone.twltrus.tdr.model.Rehab
import org.jsoup.Jsoup
import org.jsoup.select.Elements

object RehabApi {

    private const val tdlUrl = "http://www.tokyodisneyresort.jp/tdl/monthly/stop.html"
    private const val tdsUrl = "http://www.tokyodisneyresort.jp/tds/monthly/stop.html"

    fun getTdl(): List<Rehab> {
        val doc = Jsoup.connect(tdlUrl).followRedirects(true).regularHeader().get()
        val list = doc.select("div.linkList6 > ul > li")
        return analysis(list)
    }

    fun getTds(): List<Rehab> {
        val doc = Jsoup.connect(tdsUrl).followRedirects(true).regularHeader().get()
        val list = doc.select("div.linkList6 > ul > li")
        return analysis(list)
    }

    private fun analysis(list: Elements): List<Rehab> {
        return list.map { rehab ->
            val name = rehab.select("p:not(.date)").text()
            val date = rehab.select("p.date").text()
            val url = rehab.select("a").attr("href")
            val link = "http://www.tokyodisneyresort.jp$url"
            Rehab(name, date, link)
        }.filter { it.name != "休止を予定しているものはありません。" }
    }
}