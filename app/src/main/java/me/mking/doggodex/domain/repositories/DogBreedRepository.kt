package me.mking.doggodex.domain.repositories

import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.entities.DogBreedImageEntity

interface DogBreedRepository {
    suspend fun getDogBreeds(): List<DogBreedEntity>
    suspend fun getDogBreedImages(
        breedEntity: DogBreedEntity,
        imageCount: Int
    ): List<DogBreedImageEntity>
}