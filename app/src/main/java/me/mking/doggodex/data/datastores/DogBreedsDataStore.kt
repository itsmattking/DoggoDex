package me.mking.doggodex.data.datastores

import me.mking.doggodex.data.models.DogBreedListResponse
import retrofit2.http.GET

interface DogBreedsDataStore {
    @GET("/breeds/list/all")
    suspend fun getAllBreeds(): DogBreedListResponse
}