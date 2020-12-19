package me.mking.doggodex.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.common.presentation.SingleLiveEvent
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.usecases.GetDogBreedsUseCase
import me.mking.doggodex.presentation.DogBreedInput
import me.mking.doggodex.presentation.viewstate.BrowseNavigation
import me.mking.doggodex.presentation.viewstate.BrowseViewState
import me.mking.doggodex.presentation.viewstate.DogBreedViewData

class BrowseViewModel @ViewModelInject constructor(
    private val getDogBreedsUseCase: GetDogBreedsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<BrowseViewState>()
    val state: LiveData<BrowseViewState> = _state

    private val _navigation: SingleLiveEvent<BrowseNavigation> =
        SingleLiveEvent()
    val navigation: LiveData<BrowseNavigation> = _navigation

    private val dogBreeds: MutableList<DogBreedEntity> = mutableListOf()

    fun loadDogBreeds() {
        if (dogBreeds.isNotEmpty()) {
            return
        }
        _state.value = BrowseViewState.Loading
        viewModelScope.launch {
            when (val result = getDogBreedsUseCase.execute()) {
                is DataResult.Error -> {
                    _state.value = BrowseViewState.Error
                }
                is DataResult.Success -> {
                    dogBreeds.clear()
                    dogBreeds.addAll(result.data)
                    _state.value = BrowseViewState.Ready(
                        dogBreeds.map {
                            DogBreedViewData(
                                breedName = it.name
                            )
                        }
                    )
                }
            }
        }
    }

    fun onDogBreedClicked(position: Int) {
        val dogBreedEntity = dogBreeds[position]
        _navigation.value = BrowseNavigation.ToBreedImages(
            DogBreedInput(
                dogBreedEntity.name,
                dogBreedEntity.breed,
                dogBreedEntity.subBreed
            )
        )
    }
}