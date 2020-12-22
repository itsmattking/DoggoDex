package me.mking.doggodex.common

object Extensions {
    fun <T> MutableList<T>.replaceWith(other: List<T>) {
        clear()
        addAll(other)
    }
}