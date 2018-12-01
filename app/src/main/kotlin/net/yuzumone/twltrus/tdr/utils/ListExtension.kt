package net.yuzumone.twltrus.tdr.utils

fun <T> List<T>.convertArrayList(): ArrayList<T> {
    if (this.isEmpty()) {
        ArrayList<T>()
    }
    val result = ArrayList<T>()
    this.forEach { item ->
        result.add(item)
    }
    return result
}