package me.mking.doggodex.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.mking.doggodex.domain.usecases.GetDogBreedsUseCase
import me.mking.doggodex.presentation.viewstate.BrowseViewState
import javax.inject.Inject

class BrowseViewModel @ViewModelInject constructor(
    private val getDogBreedsUseCase: GetDogBreedsUseCase
) : ViewModel() {
    private val _state = MutableLiveData<BrowseViewState>()
    val state: LiveData<BrowseViewState> = _state

    init {
        viewModelScope.launch {
            val result = getDogBreedsUseCase.execute()
            _state.value = BrowseViewState.Ready
        }
    }
}