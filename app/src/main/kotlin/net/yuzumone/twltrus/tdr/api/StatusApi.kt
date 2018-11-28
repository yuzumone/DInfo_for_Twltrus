package net.yuzumone.twltrus.tdr.api

import net.yuzumone.twltrus.tdr.extension.regularHeader
import org.jsoup.Jsoup

object StatusApi {

    private const val tdlUrl =
            "https://www.tokyodisneyresort.jp/view_interface.php?blockId=94199&pageBlockId=135360"
    private const val tdsUrl =
            "https://www.tokyodisneyresort.jp/view_interface.php?blockId=94199&pageBlockId=135410"

    fun getTdlStatus(): String {
        return Jsoup.connect(tdlUrl).regularHeader().ignoreContentType(true).get().select("p").text()
    }

    fun getTdsStatus(): String {
        return Jsoup.connect(tdsUrl).regularHeader().ignoreContentType(true).get().select("p").text()
    }

    fun isCloseTdl(): Boolean {
        val doc = Jsoup.connect(tdlUrl).regularHeader().ignoreContentType(true).get()
        val item = doc.select("p")
        return item.text() == "ただいま東京ディズニーランドは、閉園しております。"
    }

    fun isCloseTds(): Boolean {
        val doc = Jsoup.connect(tdsUrl).regularHeader().ignoreContentType(true).get()
        val item = doc.select("p")
        return item.text() == "ただいま東京ディズニーシーは、閉園しております。"
    }
}