package me.mking.doggodex.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.mking.doggodex.databinding.FragmentDogBreedImagesBinding
import me.mking.doggodex.presentation.DogBreedInput
import me.mking.doggodex.presentation.viewmodel.DogBreedImagesViewModel
import me.mking.doggodex.presentation.viewstate.DogBreedImagesViewState

private const val ARG_DOG_BREED_INPUT = "dogBreedInput"

@AndroidEntryPoint
class DogBreedImagesFragment : Fragment() {
    private var dogBreedInput: DogBreedInput? = null

    private val viewModel: DogBreedImagesViewModel by viewModels()

    private lateinit var viewBinding: FragmentDogBreedImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dogBreedInput = it.getParcelable(ARG_DOG_BREED_INPUT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDogBreedImagesBinding.inflate(inflater).apply {
        viewBinding = this
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { handleState(it) }
        viewModel.loadDogBreedImages(dogBreedInput!!)
    }

    private fun handleState(state: DogBreedImagesViewState) {
        viewBinding.dogBreedImagesProgressBar.isVisible = false
        when (state) {
            DogBreedImagesViewState.Loading -> viewBinding.dogBreedImagesProgressBar.isVisible =
                true
            is DogBreedImagesViewState.Ready -> handleReadyState(state)
            DogBreedImagesViewState.Error -> Unit
        }
    }

    private fun handleReadyState(state: DogBreedImagesViewState.Ready) {
        viewBinding.dogBreedImagesRecycler.apply {
            adapter = DogBreedImagesRecyclerAdapter(state.dogBreedImages)
        }
    }
}