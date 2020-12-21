package me.mking.doggodex.ui.browse

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class DogBreedImagesRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var preloadingScrollListener: PreloadingScrollListener? = null

    init {
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(this)
        setHasFixedSize(true)
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        if (adapter is PreLoadingRecyclerAdapter) {
            preloadingScrollListener?.let { removeOnScrollListener(it) }
            preloadingScrollListener = PreloadingScrollListener()
            preloadingScrollListener?.let { addOnScrollListener(it) }
        }
    }

    override fun onDetachedFromWindow() {
        preloadingScrollListener?.let { removeOnScrollListener(it) }
        super.onDetachedFromWindow()
    }

    class PreloadingScrollListener : OnScrollListener() {
        private var direction: Direction = Direction.Forward

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == SCROLL_STATE_IDLE) {
                (recyclerView.adapter as? PreLoadingRecyclerAdapter)?.apply {
                    preload(
                        recyclerView.context,
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition(),
                        direction,
                    )
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            direction = if (dx < 0) Direction.Backward else Direction.Forward
        }
    }

}