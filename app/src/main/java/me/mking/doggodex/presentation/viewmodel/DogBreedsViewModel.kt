package me.mking.doggodex.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.mking.doggodex.common.Extensions.replaceWith
import me.mking.doggodex.common.presentation.SingleLiveEvent
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.usecases.GetDogBreedsUseCase
import me.mking.doggodex.presentation.DogBreedInput
import me.mking.doggodex.presentation.mapper.DogBreedsViewStateMapper
import me.mking.doggodex.presentation.viewstate.BrowseNavigation
import me.mking.doggodex.presentation.viewstate.BrowseViewState

class DogBreedsViewModel @ViewModelInject constructor(
    private val getDogBreedsUseCase: GetDogBreedsUseCase,
    private val dogBreedsViewStateMapper: DogBreedsViewStateMapper
) : ViewModel() {

    private val _state = MutableLiveData<BrowseViewState>()
    val state: LiveData<BrowseViewState> = _state

    private val _navigation: SingleLiveEvent<BrowseNavigation> =
        SingleLiveEvent()
    val navigation: LiveData<BrowseNavigation> = _navigation

    private val dogBreeds: MutableList<DogBreedEntity> = mutableListOf()

    fun loadDogBreeds() {
        if (alreadyLoaded()) {
            return
        }
        _state.value = BrowseViewState.Loading
        viewModelScope.launch {
            val result = getDogBreedsUseCase.execute()
            dogBreeds.replaceWith(dogBreedsViewStateMapper.mapToEntities(result))
            _state.value = dogBreedsViewStateMapper.map(result)
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

    private fun alreadyLoaded() = with(_state.value) {
        this is BrowseViewState.Ready
                && this.breeds.isNotEmpty()
    }
}