package me.mking.doggodex.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import dagger.hilt.android.AndroidEntryPoint
import me.mking.doggodex.R
import me.mking.doggodex.presentation.viewmodel.BrowseViewModel
import me.mking.doggodex.presentation.viewstate.BrowseViewState

@AndroidEntryPoint
class BrowseFragment : Fragment() {

    companion object {
        fun newInstance() = BrowseFragment()
    }

    private val viewModel: BrowseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.browse_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                BrowseViewState.Loading -> Unit
                BrowseViewState.Ready -> Unit
            }
        }
    }

}