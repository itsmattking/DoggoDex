package me.mking.doggodex.presentation.ui

import android.content.Context

interface PreLoadingRecyclerAdapter {
    fun preload(context: Context, currentPosition: Int, direction: Direction)
}

sealed class Direction(val num: Int) {
    object Forward : Direction(1)
    object Backward : Direction(-1)
}