package me.mking.doggodex.common.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BindingViewHolder<S, T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract val viewBinding: S
    abstract fun bind(data: T)
}