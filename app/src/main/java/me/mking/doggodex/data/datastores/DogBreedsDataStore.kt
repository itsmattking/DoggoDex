package me.mking.doggodex.data.datastores

import me.mking.doggodex.data.models.DogBreedImagesResponse
import me.mking.doggodex.data.models.DogBreedListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedsDataStore {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): DogBreedListResponse

    @GET("breed/{breedName}/images/random/{imageCount}")
    suspend fun getRandomBreedImages(
        @Path("breedName") breedName: String,
        @Path("imageCount") imageCount: Int
    ): DogBreedImagesResponse
}