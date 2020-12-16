package me.mking.doggodex.data.mappers

import me.mking.doggodex.data.models.DogBreedListResponse
import me.mking.doggodex.domain.entities.DogBreedEntity
import java.util.*

class DogBreedListResponseMapper {
    fun map(dogBreedListResponse: DogBreedListResponse): List<DogBreedEntity> {
        return dogBreedListResponse.message.map { (breed, subBreeds) ->
            val breedName = breed.capitalize(Locale.UK)
            subBreeds.map { sub ->
                DogBreedEntity("${sub.capitalize(Locale.UK)} $breedName")
            }.orIfEmpty(DogBreedEntity(breedName))
        }.flatten().sortedBy { it.name }
    }
}
private fun <T> List<T>.orIfEmpty(vararg items: T): List<T> {
    return if (isEmpty()) items.toList() else this
}