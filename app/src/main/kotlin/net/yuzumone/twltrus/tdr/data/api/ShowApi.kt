package net.yuzumone.twltrus.tdr.data.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import net.yuzumone.twltrus.tdr.utils.regularHeader
import net.yuzumone.twltrus.tdr.model.RealtimeShow
import net.yuzumone.twltrus.tdr.model.Show
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.util.*

object ShowApi {

    private const val tdlUrl = "http://www.tokyodisneyresort.jp/_/realtime/tdl_parade_show.json"
    private const val tdsUrl = "http://www.tokyodisneyresort.jp/_/realtime/tds_parade_show.json"

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

    fun getRealtimeTdl(cookie: String): List<RealtimeShow> {
        val body = Jsoup.connect(tdlUrl).cookie("tdrloc", cookie)
                .followRedirects(true).ignoreContentType(true)
                .regularHeader().execute().body()
        return analysisRealtime(body)
    }

    fun getRealtimeTds(cookie: String): List<RealtimeShow> {
        val body = Jsoup.connect(tdsUrl).cookie("tdrloc", cookie)
                .followRedirects(true).ignoreContentType(true)
                .regularHeader().execute().body()
        return analysisRealtime(body)
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

    private fun analysisRealtime(json: String): List<RealtimeShow> {
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        val type = Types.newParameterizedType(List::class.java, RealtimeShow::class.java)
        val adapter: JsonAdapter<List<RealtimeShow>> = moshi.adapter(type)
        return adapter.fromJson(json)!!
    }
}