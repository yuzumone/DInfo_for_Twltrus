package net.yuzumone.twltrus.tdr.data.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import net.yuzumone.twltrus.tdr.utils.regularHeader
import net.yuzumone.twltrus.tdr.model.Restaurant
import org.jsoup.Jsoup

object RestaurantApi {

    private const val tdlUrl = "http://www.tokyodisneyresort.jp/_/realtime/tdl_restaurant.json"
    private const val tdsUrl = "http://www.tokyodisneyresort.jp/_/realtime/tds_restaurant.json"

    fun getTdl(cookie: String): List<Restaurant> {
        val body = Jsoup.connect(tdlUrl).cookie("tdrloc", cookie)
                .followRedirects(true).ignoreContentType(true)
                .regularHeader().execute().body()
        return analysis(body)
    }

    fun getTds(cookie: String): List<Restaurant> {
        val body = Jsoup.connect(tdsUrl).cookie("tdrloc", cookie)
                .followRedirects(true).ignoreContentType(true)
                .regularHeader().execute().body()
        return analysis(body)
    }

    private fun analysis(json: String): List<Restaurant> {
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        val type = Types.newParameterizedType(List::class.java, Restaurant::class.java)
        val adapter: JsonAdapter<List<Restaurant>> = moshi.adapter(type)
        return adapter.fromJson(json)!!
    }

}