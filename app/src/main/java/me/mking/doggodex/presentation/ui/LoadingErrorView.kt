package me.mking.doggodex.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import me.mking.doggodex.R

class LoadingErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.loading_error_view, this, true)
        orientation = VERTICAL
    }
}