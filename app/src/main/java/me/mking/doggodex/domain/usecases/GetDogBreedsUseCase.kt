package me.mking.doggodex.domain.usecases

import me.mking.doggodex.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.repositories.DogBreedRepository

class GetDogBreedsUseCase(private val dogBreedRepository: DogBreedRepository) {
    suspend fun execute(): DataResult<List<DogBreedEntity>> {
        return dogBreedRepository.getDogBreeds()
    }
}