package me.mking.doggodex.common.ui

import androidx.recyclerview.widget.RecyclerView

abstract class BindingRecyclerAdapter<S, T : BindingViewHolder<*, S>>(
    open val data: List<S> = emptyList()
) : RecyclerView.Adapter<T>()