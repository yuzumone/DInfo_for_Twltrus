package net.yuzumone.twltrus.tdr.api

import net.yuzumone.twltrus.tdr.extension.regularHeader
import net.yuzumone.twltrus.tdr.model.Rehab
import org.jsoup.Jsoup
import org.jsoup.select.Elements

object RehabApi {

    private val url = "http://info.tokyodisneyresort.jp/schedule/stop/stop_list.html"

    fun getTdl(): List<Rehab> {
        val doc = Jsoup.connect(url).followRedirects(true).regularHeader().get()
        val list = doc.select("section.tdl > dl > dd > ul> li")
        return analysis(list)
    }

    fun getTds(): List<Rehab> {
        val doc = Jsoup.connect(url).followRedirects(true).regularHeader().get()
        val list = doc.select("section.tds > dl > dd > ul> li")
        return analysis(list)
    }

    private fun analysis(list: Elements): List<Rehab> {
        return list.map { rehab ->
            val name = if (rehab.select("a").text() != "") rehab.select("a").text() else rehab.select("p").text()
            val date = rehab.select("span").text()
            val url = rehab.select("a").attr("href")
            Rehab(name, date, url)
        }
    }
}