package me.mking.doggodex.domain.entities

data class DogBreedEntity(
    val name: String,
    val breed: String,
    val subBreed: String? = null
)