package me.mking.doggodex.ui.browse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import me.mking.doggodex.R
import me.mking.doggodex.common.ui.BindingRecyclerAdapter
import me.mking.doggodex.common.ui.BindingViewHolder
import me.mking.doggodex.databinding.DogBreedImageRecyclerItemBinding
import me.mking.doggodex.presentation.viewstate.DogBreedImagesViewState

class DogBreedImagesRecyclerAdapter(override val data: List<DogBreedImagesViewState.DogBreedImageViewData>) :
    BindingRecyclerAdapter<DogBreedImagesViewState.DogBreedImageViewData, DogBreedImagesRecyclerAdapter.DogBreedImagesViewHolder>() {

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
                .fitCenter()
                .into(viewBinding.dogBreedImageRecyclerItemImage)
        }
    }
}