package me.mking.doggodex.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.mking.doggodex.R
import me.mking.doggodex.common.ui.BindingRecyclerAdapter
import me.mking.doggodex.common.ui.BindingViewHolder
import me.mking.doggodex.databinding.DogBreedImageRecyclerItemBinding
import me.mking.doggodex.presentation.viewstate.DogBreedImagesViewState

class DogBreedImagesRecyclerAdapter(override val data: List<DogBreedImagesViewState.DogBreedImageViewData>) :
    PreLoadingRecyclerAdapter,
    BindingRecyclerAdapter<DogBreedImagesViewState.DogBreedImageViewData, DogBreedImagesRecyclerAdapter.DogBreedImagesViewHolder>() {

    private val preloaded = MutableList(data.size) { false }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        preload(recyclerView.context, 0, Direction.Forward)
    }

    override fun preload(context: Context, currentPosition: Int, direction: Direction) {
        preloaded[currentPosition] = true
        val preloadPosition = currentPosition + direction.num
        if (preloadPosition < 0 || preloadPosition >= data.size || preloaded[preloadPosition]) {
            return
        }
        preloaded[preloadPosition] = true
        Glide.with(context)
            .load(data[preloadPosition].url)
            .centerInside()
            .preload()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DogBreedImagesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dog_breed_image_recycler_item, parent, false)
        )

    override fun onBindViewHolder(holder: DogBreedImagesViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount() = data.size

    class DogBreedImagesViewHolder(itemView: View) :
        BindingViewHolder<DogBreedImageRecyclerItemBinding, DogBreedImagesViewState.DogBreedImageViewData>(
            itemView
        ) {
        override val viewBinding = DogBreedImageRecyclerItemBinding.bind(itemView)

        override fun bind(data: DogBreedImagesViewState.DogBreedImageViewData) {
            Glide.with(itemView.context)
                .load(data.url)
                .centerInside()
                .into(viewBinding.dogBreedImageRecyclerItemImage)
        }
    }
}