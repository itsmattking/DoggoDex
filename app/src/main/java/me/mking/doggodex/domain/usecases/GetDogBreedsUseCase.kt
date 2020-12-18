package me.mking.doggodex.domain.usecases

import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.common.domain.NoInputUseCase
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.repositories.DogBreedRepository
import javax.inject.Inject

class GetDogBreedsUseCase @Inject constructor(
    private val dogBreedRepository: DogBreedRepository
) : NoInputUseCase<List<DogBreedEntity>> {
    override suspend fun execute(): DataResult<List<DogBreedEntity>> {
        return try {
            DataResult.Success(dogBreedRepository.getDogBreeds())
        } catch (exception: Exception) {
            DataResult.Error("Error getting breed: $exception")
        }
    }
}