package me.mking.doggodex.data.repositories

import me.mking.doggodex.data.datastores.DogBreedsDataStore
import me.mking.doggodex.data.mappers.DogBreedImagesResponseMapper
import me.mking.doggodex.data.mappers.DogBreedListResponseMapper
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.entities.DogBreedImageEntity
import me.mking.doggodex.domain.repositories.DogBreedRepository
import javax.inject.Inject

class DogBreedRepositoryImpl @Inject constructor(
    private val dataStore: DogBreedsDataStore,
    private val dogBreedListResponseMapper: DogBreedListResponseMapper,
    private val dogBreedImagesResponseMapper: DogBreedImagesResponseMapper
) : DogBreedRepository {
    override suspend fun getDogBreeds(): List<DogBreedEntity> {
        return dogBreedListResponseMapper.map(dataStore.getAllBreeds())
    }

    override suspend fun getDogBreedImages(
        breedEntity: DogBreedEntity,
        imageCount: Int
    ): List<DogBreedImageEntity> {
        return dogBreedImagesResponseMapper.map(
            dataStore.getRandomBreedImages(
                listOfNotNull(breedEntity.breed, breedEntity.subBreed).joinToString("/"),
                imageCount = imageCount
            )
        )
    }
}