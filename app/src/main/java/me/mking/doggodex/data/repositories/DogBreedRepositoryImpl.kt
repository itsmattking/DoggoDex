package me.mking.doggodex.data.repositories

import me.mking.doggodex.data.DataResult
import me.mking.doggodex.data.datastores.DogBreedsDataStore
import me.mking.doggodex.data.mappers.DogBreedListResponseMapper
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.repositories.DogBreedRepository

class DogBreedRepositoryImpl(
    private val dataStore: DogBreedsDataStore,
    private val mapper: DogBreedListResponseMapper
) : DogBreedRepository {
    override suspend fun getDogBreeds(): DataResult<List<DogBreedEntity>> {
        return try {
            DataResult.Success(mapper.map(dataStore.getAllBreeds()))
        } catch (exception: Exception) {
            DataResult.Error("Unable to fetch dog breeds: ${exception.message}")
        }
    }
}