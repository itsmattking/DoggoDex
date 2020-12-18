package me.mking.doggodex.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.mking.doggodex.R
import me.mking.doggodex.databinding.BrowseFragmentBinding
import me.mking.doggodex.presentation.viewmodel.BrowseViewModel
import me.mking.doggodex.presentation.viewstate.BrowseNavigation
import me.mking.doggodex.presentation.viewstate.BrowseViewState

@AndroidEntryPoint
class BrowseFragment : Fragment() {

    companion object {
        fun newInstance() = BrowseFragment()
    }

    private val viewModel: BrowseViewModel by viewModels()

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
        when (state) {
            BrowseViewState.Loading -> viewBinding.browseProgressBar.isVisible = true
            is BrowseViewState.Ready -> handleReadyState(state)
            BrowseViewState.Error -> Unit
        }
    }

    private fun handleReadyState(state: BrowseViewState.Ready) {
        viewBinding.browseProgressBar.isVisible = false
        viewBinding.dogBreedRecycler.adapter = DogBreedRecyclerAdapter(state.breeds) {
            viewModel.onDogBreedClicked(it)
        }
    }

    private fun handleNavigation(navigation: BrowseNavigation) {
        when (navigation) {
            is BrowseNavigation.ToBreedImages -> Unit
        }
    }

}