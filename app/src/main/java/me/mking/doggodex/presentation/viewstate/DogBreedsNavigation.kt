package me.mking.doggodex.presentation.viewstate

import me.mking.doggodex.presentation.viewmodel.DogBreedInput

sealed class DogBreedsNavigation {
    data class ToBreedImages(val dogBreedInput: DogBreedInput) : DogBreedsNavigation()
}