package me.mking.doggodex.data.mappers

import me.mking.doggodex.data.models.DogBreedListResponse
import me.mking.doggodex.domain.entities.DogBreedEntity
import java.util.*
import javax.inject.Inject

class DogBreedListResponseMapper @Inject constructor() {
    fun map(dogBreedListResponse: DogBreedListResponse): List<DogBreedEntity> {
        return dogBreedListResponse.message.map { (breed, subBreeds) ->
            val breedName = breed.capitalize(Locale.UK)
            subBreeds.map { sub ->
                DogBreedEntity(
                    name = "${sub.capitalize(Locale.UK)} $breedName",
                    breed = breed,
                    subBreed = sub
                )
            }.orIfEmpty(DogBreedEntity(breedName, breed))
        }.flatten().sortedBy { it.name }
    }
}

private fun <T> List<T>.orIfEmpty(vararg items: T): List<T> {
    return if (isEmpty()) items.toList() else this
}