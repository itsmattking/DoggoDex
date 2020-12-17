package me.mking.doggodex.ui.browse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.mking.doggodex.R
import me.mking.doggodex.databinding.DogBreedRecyclerItemBinding
import me.mking.doggodex.presentation.viewstate.BrowseViewState

typealias OnDogBreedClick = (Int) -> Unit

class DogBreedRecyclerAdapter(
    private val data: List<BrowseViewState.DogBreedViewData>,
    private val onDogBreedClick: OnDogBreedClick? = null
) :
    RecyclerView.Adapter<DogBreedRecyclerAdapter.DogBreedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedViewHolder {
        return DogBreedViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dog_breed_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DogBreedViewHolder, position: Int) {
        holder.bind(data[position], onDogBreedClick)
    }

    override fun getItemCount() = data.size

    class DogBreedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewBinding: DogBreedRecyclerItemBinding =
            DogBreedRecyclerItemBinding.bind(itemView)

        fun bind(data: BrowseViewState.DogBreedViewData, onDogBreedClick: OnDogBreedClick?) {
            viewBinding.dogBreedRecyclerItemTitle.text = data.breedName
            viewBinding.dogBreedRecyclerItem.setOnClickListener {
                onDogBreedClick?.invoke(adapterPosition)
            }
        }
    }
}