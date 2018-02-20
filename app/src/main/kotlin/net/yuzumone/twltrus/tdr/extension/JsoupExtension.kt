package net.yuzumone.twltrus.tdr.extension

import org.jsoup.Connection

private const val agent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) " +
        "Chrome/63.0.3239.132 Safari/537.36"
private const val accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"
private const val encoding = "gzip, deflate"
private const val language = "ja-JP,ja;q=0.9,en-US;q=0.8,en;q=0.7"

fun Connection.regularHeader(): Connection {
    return this.userAgent(agent)
            .header("Accept", accept)
            .header("Accept-Encoding", encoding)
            .header("Accept-Language", language)
            .header("Upgrade-Insecure-Requests", "1")
            .header("Connection", "keep-alive")
}
