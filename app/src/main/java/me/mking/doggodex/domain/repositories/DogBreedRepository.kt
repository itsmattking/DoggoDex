package me.mking.doggodex.domain.repositories

import me.mking.doggodex.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity

interface DogBreedRepository {
    suspend fun getDogBreeds(): DataResult<List<DogBreedEntity>>
}