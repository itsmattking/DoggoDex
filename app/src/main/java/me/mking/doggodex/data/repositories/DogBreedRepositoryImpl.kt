package me.mking.doggodex.data.repositories

import me.mking.doggodex.data.mappers.DogBreedImagesResponseMapper
import me.mking.doggodex.data.mappers.DogBreedListResponseMapper
import me.mking.doggodex.data.models.DogBreedImagesResponse
import me.mking.doggodex.data.models.DogBreedListResponse
import me.mking.doggodex.data.services.DogBreedsService
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.entities.DogBreedImageEntity
import me.mking.doggodex.domain.repositories.DogBreedRepository
import javax.inject.Inject

class DogBreedRepositoryImpl @Inject constructor(
    private val dogBreedServices: DogBreedsService,
    private val dogBreedListResponseMapper: DogBreedListResponseMapper,
    private val dogBreedImagesResponseMapper: DogBreedImagesResponseMapper
) : DogBreedRepository {
    override suspend fun getDogBreeds(): List<DogBreedEntity> {
        val result = dogBreedServices.getAllBreeds()
        if (result.status != DogBreedListResponse.STATUS_SUCCESS) {
            throw DogBreedsServiceException()
        }
        return dogBreedListResponseMapper.map(dogBreedServices.getAllBreeds())
    }

    override suspend fun getDogBreedImages(
        breedEntity: DogBreedEntity,
        imageCount: Int
    ): List<DogBreedImageEntity> {
        val result = dogBreedServices.getRandomBreedImages(
            listOfNotNull(breedEntity.breed, breedEntity.subBreed).joinToString("/"),
            imageCount = imageCount
        )
        if (result.status != DogBreedImagesResponse.STATUS_SUCCESS) {
            throw DogBreedsServiceException()
        }
        return dogBreedImagesResponseMapper.map(result)
    }
}

class DogBreedsServiceException(msg: String = "Error occurred in response") : Exception(msg)