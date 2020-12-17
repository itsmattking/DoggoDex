package me.mking.doggodex.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.mking.doggodex.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.usecases.GetDogBreedImagesUseCase

class DogBreedImagesViewModel @ViewModelInject constructor(
    private val getDogBreedImagesUseCase: GetDogBreedImagesUseCase
) : ViewModel() {

    fun loadDogBreedImages(dogBreedEntity: DogBreedEntity) {
        viewModelScope.launch {
            val result = getDogBreedImagesUseCase.execute(dogBreedEntity, 10)
            when (result) {
                is DataResult.Error -> Unit
                is DataResult.Success -> Unit
            }
        }
    }

}