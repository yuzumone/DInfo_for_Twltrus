package net.yuzumone.twltrus.tdr.api

import net.yuzumone.twltrus.tdr.extension.regularHeader
import org.jsoup.Jsoup

object StatusApi {

    private val tdlUrl = "http://info.tokyodisneyresort.jp/s/today/sp_tdl_ticket.html"
    private val tdsUrl = "http://info.tokyodisneyresort.jp/s/today/sp_tds_ticket.html"

    fun getTdlStatus(): String {
        return Jsoup.connect(tdlUrl).regularHeader().get().select("p").text()
    }

    fun getTdsStatus(): String {
        return Jsoup.connect(tdsUrl).regularHeader().get().select("p").text()
    }

    fun isCloseTdl(): Boolean {
        val doc = Jsoup.connect(tdlUrl).regularHeader().get()
        val item = doc.select("p")
        return item.text() == "ただいま東京ディズニーランドは、閉園しております。"
    }

    fun isCloseTds(): Boolean {
        val doc = Jsoup.connect(tdsUrl).regularHeader().get()
        val item = doc.select("p")
        return item.text() == "ただいま東京ディズニーシーは、閉園しております。"
    }
}