package me.mking.doggodex.data.models

data class DogBreedListResponse(
    val message: Map<String, List<String>>,
    val status: String
)