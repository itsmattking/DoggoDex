package me.mking.doggodex.domain.usecases

import me.mking.doggodex.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.entities.DogBreedImageEntity
import me.mking.doggodex.domain.repositories.DogBreedRepository
import javax.inject.Inject

class GetDogBreedImagesUseCase @Inject constructor(
    private val dogBreedRepository: DogBreedRepository
) {
    suspend fun execute(
        dogBreedEntity: DogBreedEntity,
        imageCount: Int
    ): DataResult<List<DogBreedImageEntity>> {
        return try {
            DataResult.Success(dogBreedRepository.getDogBreedImages(dogBreedEntity, imageCount))
        } catch (exception: Exception) {
            DataResult.Error("Unable to fetch images: $exception")
        }
    }
}