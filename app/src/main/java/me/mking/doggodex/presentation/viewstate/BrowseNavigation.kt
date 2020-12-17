package me.mking.doggodex.presentation.viewstate

import me.mking.doggodex.domain.entities.DogBreedEntity

sealed class BrowseNavigation {
    data class ToBreedImages(val breedEntity: DogBreedEntity) : BrowseNavigation()
}