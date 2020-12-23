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
import me.mking.doggodex.presentation.mapper.DogBreedsViewStateMapper
import me.mking.doggodex.presentation.viewstate.DogBreedsNavigation
import me.mking.doggodex.presentation.viewstate.DogBreedsViewState

class DogBreedsViewModel @ViewModelInject constructor(
    private val getDogBreedsUseCase: GetDogBreedsUseCase,
    private val dogBreedsViewStateMapper: DogBreedsViewStateMapper
) : ViewModel() {

    private val _state = MutableLiveData<DogBreedsViewState>()
    val state: LiveData<DogBreedsViewState> = _state

    private val _navigation: SingleLiveEvent<DogBreedsNavigation> =
        SingleLiveEvent()
    val navigation: LiveData<DogBreedsNavigation> = _navigation

    private val dogBreeds: MutableList<DogBreedEntity> = mutableListOf()

    fun loadDogBreeds() {
        if (alreadyLoaded()) {
            return
        }
        _state.value = DogBreedsViewState.Loading
        viewModelScope.launch {
            val result = getDogBreedsUseCase.execute()
            dogBreeds.replaceWith(dogBreedsViewStateMapper.mapToEntities(result))
            _state.value = dogBreedsViewStateMapper.map(result)
        }
    }

    fun onDogBreedClicked(position: Int) {
        if (position < 0 || position >= dogBreeds.size) {
            return
        }
        val dogBreedEntity = dogBreeds[position]
        _navigation.value = DogBreedsNavigation.ToBreedImages(
            DogBreedInput(
                dogBreedEntity.name,
                dogBreedEntity.breed,
                dogBreedEntity.subBreed
            )
        )
    }

    private fun alreadyLoaded() = dogBreeds.isNotEmpty()
}