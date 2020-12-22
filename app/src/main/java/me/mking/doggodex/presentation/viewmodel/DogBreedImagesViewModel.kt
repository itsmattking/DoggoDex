package me.mking.doggodex.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.usecases.GetDogBreedImagesInput
import me.mking.doggodex.domain.usecases.GetDogBreedImagesUseCase
import me.mking.doggodex.presentation.DogBreedInput
import me.mking.doggodex.presentation.mapper.DogBreedImagesViewStateMapper
import me.mking.doggodex.presentation.viewstate.DogBreedImagesViewState

class DogBreedImagesViewModel @ViewModelInject constructor(
    private val getDogBreedImagesUseCase: GetDogBreedImagesUseCase,
    private val dogBreedImagesViewStateMapper: DogBreedImagesViewStateMapper
) : ViewModel() {

    private val _state: MutableLiveData<DogBreedImagesViewState> = MutableLiveData()
    val state: LiveData<DogBreedImagesViewState> = _state

    fun loadDogBreedImages(dogBreedInput: DogBreedInput) {
        if (alreadyLoaded()) {
            return
        }
        _state.value = DogBreedImagesViewState.Loading
        viewModelScope.launch {
            val result = getDogBreedImagesUseCase.execute(
                GetDogBreedImagesInput(
                    DogBreedEntity(
                        name = dogBreedInput.name,
                        breed = dogBreedInput.breed,
                        subBreed = dogBreedInput.subBreed
                    ), 10
                )
            )
            _state.value = dogBreedImagesViewStateMapper.map(dogBreedInput, result)
        }
    }

    private fun alreadyLoaded() = with(_state.value) {
        this is DogBreedImagesViewState.Ready
            && this.dogBreedImages.isNotEmpty()
    }
}