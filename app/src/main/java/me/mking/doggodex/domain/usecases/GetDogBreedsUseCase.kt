package me.mking.doggodex.domain.usecases

import me.mking.doggodex.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.repositories.DogBreedRepository
import javax.inject.Inject

class GetDogBreedsUseCase @Inject constructor(
    private val dogBreedRepository: DogBreedRepository
) {
    suspend fun execute(): DataResult<List<DogBreedEntity>> {
        return try {
            DataResult.Success(dogBreedRepository.getDogBreeds())
        } catch (exception: Exception) {
            DataResult.Error("Error getting breed: $exception")
        }
    }
}