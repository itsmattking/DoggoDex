package me.mking.doggodex.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import me.mking.doggodex.R
import me.mking.doggodex.presentation.viewmodel.BrowseViewModel
import me.mking.doggodex.presentation.viewstate.BrowseViewState

class BrowseFragment : Fragment() {

    companion object {
        fun newInstance() = BrowseFragment()
    }

    private lateinit var viewModel: BrowseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.browse_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BrowseViewModel::class.java)
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                BrowseViewState.Loading -> Unit
                BrowseViewState.Ready -> Unit
            }
        }
    }

}