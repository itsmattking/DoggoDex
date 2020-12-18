package me.mking.doggodex.domain.usecases

import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.common.domain.UseCase
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.entities.DogBreedImageEntity
import me.mking.doggodex.domain.repositories.DogBreedRepository
import javax.inject.Inject

class GetDogBreedImagesUseCase @Inject constructor(
    private val dogBreedRepository: DogBreedRepository
) : UseCase<GetDogBreedImagesInput, List<DogBreedImageEntity>> {
    override suspend fun execute(
        input: GetDogBreedImagesInput
    ): DataResult<List<DogBreedImageEntity>> {
        return try {
            DataResult.Success(
                dogBreedRepository.getDogBreedImages(
                    input.dogBreedEntity,
                    input.imageCount
                )
            )
        } catch (exception: Exception) {
            DataResult.Error("Unable to fetch images: $exception")
        }
    }
}

data class GetDogBreedImagesInput(val dogBreedEntity: DogBreedEntity, val imageCount: Int)