package me.mking.doggodex.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.usecases.GetDogBreedImagesInput
import me.mking.doggodex.domain.usecases.GetDogBreedImagesUseCase
import me.mking.doggodex.presentation.DogBreedInput
import me.mking.doggodex.presentation.viewstate.DogBreedImagesViewState

class DogBreedImagesViewModel @ViewModelInject constructor(
    private val getDogBreedImagesUseCase: GetDogBreedImagesUseCase
) : ViewModel() {

    private val _state: MutableLiveData<DogBreedImagesViewState> = MutableLiveData()
    val state: LiveData<DogBreedImagesViewState> = _state

    fun loadDogBreedImages(dogBreedInput: DogBreedInput) {
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
            _state.value = when (result) {
                is DataResult.Error -> DogBreedImagesViewState.Error
                is DataResult.Success -> DogBreedImagesViewState.Ready(
                    dogBreedName = dogBreedInput.name,
                    dogBreedImages = result.data.map {
                        DogBreedImagesViewState.DogBreedImageViewData(
                            url = it.url
                        )
                    }
                )
            }
        }
    }
}