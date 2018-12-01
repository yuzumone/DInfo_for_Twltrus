package net.yuzumone.twltrus.tdr.data.api

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import net.yuzumone.twltrus.tdr.utils.regularHeader
import net.yuzumone.twltrus.tdr.model.*
import org.jsoup.Jsoup

object GreetingApi {

    private const val tdlUrl = "http://www.tokyodisneyresort.jp/_/realtime/tdl_greeting.json"
    private const val tdsUrl = "http://www.tokyodisneyresort.jp/_/realtime/tds_greeting.json"

    fun getTdl(cookie: String): List<Greeting> {
        val body = Jsoup.connect(tdlUrl).cookie("tdrloc", cookie)
                .followRedirects(true).ignoreContentType(true)
                .regularHeader().execute().body()
        val res = getMoshi().adapter(LGreetingResponse::class.java).fromJson(body)!!
        val list = ArrayList<Greeting>()
        res.id11?.let { it.Facility.forEach { if (it.greeting != null) list.add(it.greeting) } }
        res.id13?.let { it.Facility.forEach { if (it.greeting != null) list.add(it.greeting) } }
        res.id16?.let { it.Facility.forEach { if (it.greeting != null) list.add(it.greeting) } }
        return list
    }

    fun getTds(cookie: String): List<Greeting> {
        val body = Jsoup.connect(tdsUrl).cookie("tdrloc", cookie)
                .followRedirects(true).ignoreContentType(true)
                .regularHeader().execute().body()
        val res = getMoshi().adapter(SGreetingResponse::class.java).fromJson(body)!!
        val list = ArrayList<Greeting>()
        res.id21?.let { it.Facility.forEach { if (it.greeting != null) list.add(it.greeting) } }
        res.id22?.let { it.Facility.forEach { if (it.greeting != null) list.add(it.greeting) } }
        res.id25?.let { it.Facility.forEach { if (it.greeting != null) list.add(it.greeting) } }
        res.id26?.let { it.Facility.forEach { if (it.greeting != null) list.add(it.greeting) } }
        res.id27?.let { it.Facility.forEach { if (it.greeting != null) list.add(it.greeting) } }
        return list
    }

    private fun getMoshi(): Moshi {
        return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }
}