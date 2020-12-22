package me.mking.doggodex.presentation.viewstate

import me.mking.doggodex.presentation.DogBreedInput

sealed class DogBreedsNavigation {
    data class ToBreedImages(val dogBreedInput: DogBreedInput) : DogBreedsNavigation()
}