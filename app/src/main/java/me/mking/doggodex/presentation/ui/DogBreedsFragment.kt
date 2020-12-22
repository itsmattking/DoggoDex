package me.mking.doggodex.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.mking.doggodex.R
import me.mking.doggodex.databinding.FragmentDogBreedsBinding
import me.mking.doggodex.presentation.viewmodel.DogBreedsViewModel
import me.mking.doggodex.presentation.viewstate.DogBreedsNavigation
import me.mking.doggodex.presentation.viewstate.DogBreedsViewState

@AndroidEntryPoint
class DogBreedsFragment : Fragment() {

    companion object {
        fun newInstance() = DogBreedsFragment()
    }

    private val viewModel: DogBreedsViewModel by viewModels()

    private lateinit var viewBinding: FragmentDogBreedsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDogBreedsBinding.inflate(inflater).apply {
        viewBinding = this
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { handleState(it) }
        viewModel.navigation.observe(viewLifecycleOwner) { handleNavigation(it) }
        viewModel.loadDogBreeds()
    }

    private fun handleState(state: DogBreedsViewState) {
        viewBinding.browseProgressBar.isVisible = false
        when (state) {
            DogBreedsViewState.Loading -> viewBinding.browseProgressBar.isVisible = true
            is DogBreedsViewState.Ready -> handleReadyState(state)
            DogBreedsViewState.Error -> viewBinding.loadingError.isVisible = true
        }
    }

    private fun handleReadyState(state: DogBreedsViewState.Ready) {
        viewBinding.loadingError.isVisible = false
        viewBinding.dogBreedRecycler.adapter = DogBreedsRecyclerAdapter(state.breeds) {
            viewModel.onDogBreedClicked(it)
        }
    }

    private fun handleNavigation(navigation: DogBreedsNavigation) {
        when (navigation) {
            is DogBreedsNavigation.ToBreedImages -> findNavController().navigate(
                R.id.action_browseFragment_to_dogBreedImagesFragment,
                bundleOf("dogBreedInput" to navigation.dogBreedInput)
            )
        }
    }

}