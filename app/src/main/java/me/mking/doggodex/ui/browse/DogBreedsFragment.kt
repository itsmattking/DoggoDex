package me.mking.doggodex.ui.browse

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
import me.mking.doggodex.databinding.BrowseFragmentBinding
import me.mking.doggodex.presentation.viewmodel.DogBreedsViewModel
import me.mking.doggodex.presentation.viewstate.BrowseNavigation
import me.mking.doggodex.presentation.viewstate.BrowseViewState

@AndroidEntryPoint
class DogBreedsFragment : Fragment() {

    companion object {
        fun newInstance() = DogBreedsFragment()
    }

    private val viewModel: DogBreedsViewModel by viewModels()

    private lateinit var viewBinding: BrowseFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = BrowseFragmentBinding.inflate(inflater).apply {
        viewBinding = this
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { handleState(it) }
        viewModel.navigation.observe(viewLifecycleOwner) { handleNavigation(it) }
        viewModel.loadDogBreeds()
    }

    private fun handleState(state: BrowseViewState) {
        viewBinding.browseProgressBar.isVisible = false
        when (state) {
            BrowseViewState.Loading -> viewBinding.browseProgressBar.isVisible = true
            is BrowseViewState.Ready -> handleReadyState(state)
            BrowseViewState.Error -> Unit
        }
    }

    private fun handleReadyState(state: BrowseViewState.Ready) {
        viewBinding.browseProgressBar.isVisible = false
        viewBinding.dogBreedRecycler.adapter = DogBreedsRecyclerAdapter(state.breeds) {
            viewModel.onDogBreedClicked(it)
        }
    }

    private fun handleNavigation(navigation: BrowseNavigation) {
        when (navigation) {
            is BrowseNavigation.ToBreedImages -> findNavController().navigate(
                R.id.action_browseFragment_to_dogBreedImagesFragment,
                bundleOf("dogBreedInput" to navigation.dogBreedInput)
            )
        }
    }

}