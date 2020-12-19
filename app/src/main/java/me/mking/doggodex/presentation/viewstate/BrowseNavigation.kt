package me.mking.doggodex.presentation.viewstate

import me.mking.doggodex.presentation.DogBreedInput

sealed class BrowseNavigation {
    data class ToBreedImages(val dogBreedInput: DogBreedInput) : BrowseNavigation()
}