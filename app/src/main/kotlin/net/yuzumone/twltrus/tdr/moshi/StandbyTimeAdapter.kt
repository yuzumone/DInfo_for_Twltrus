package net.yuzumone.twltrus.tdr.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.ToJson

class StandbyTimeAdapter {

    @ToJson fun toJson(@StandbyTime time: String): String {
        return time
    }

    @FromJson @StandbyTime fun fromJson(reader: JsonReader): String? {
        var time = ""
        if (reader.peek() == JsonReader.Token.STRING) {
            time = reader.nextString()
        } else {
            reader.skipValue()
        }
        return time
    }
}