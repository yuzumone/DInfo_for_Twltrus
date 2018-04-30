package net.yuzumone.twltrus.tdr.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import net.yuzumone.twltrus.tdr.extension.regularHeader
import net.yuzumone.twltrus.tdr.model.Attraction
import net.yuzumone.twltrus.tdr.moshi.StandbyTimeAdapter
import org.jsoup.Jsoup

object AttractionApi {

    private const val tdlUrl = "https://www.tokyodisneyresort.jp/_/realtime/tdl_attraction.json"
    private const val tdsUrl = "https://www.tokyodisneyresort.jp/_/realtime/tds_attraction.json"

    fun getTdl(cookie: String): List<Attraction> {
        val body = Jsoup.connect(tdlUrl).cookie("tdrloc", cookie)
                .followRedirects(true).ignoreContentType(true)
                .regularHeader().execute().body()
        return analysis(body)
    }

    fun getTds(cookie: String): List<Attraction> {
        val body = Jsoup.connect(tdsUrl).cookie("tdrloc", cookie)
                .followRedirects(true).ignoreContentType(true)
                .regularHeader().execute().body()
        return analysis(body)
    }

    private fun analysis(json: String): List<Attraction> {
        val moshi = Moshi.Builder()
                .add(StandbyTimeAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()
        val type = Types.newParameterizedType(List::class.java, Attraction::class.java)
        val adapter: JsonAdapter<List<Attraction>> = moshi.adapter(type)
        return adapter.fromJson(json)!!
    }
}