package me.mking.doggodex.data.mappers

import me.mking.doggodex.data.models.DogBreedImagesResponse
import me.mking.doggodex.domain.entities.DogBreedImageEntity
import javax.inject.Inject

class DogBreedImagesResponseMapper @Inject constructor() {
    fun map(dogBreedImagesResponse: DogBreedImagesResponse) = dogBreedImagesResponse.message.map {
        DogBreedImageEntity(it)
    }
}