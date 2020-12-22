package me.mking.doggodex

import com.google.common.truth.Truth
import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.presentation.mapper.DogBreedsViewStateMapper
import me.mking.doggodex.presentation.viewstate.DogBreedsViewState
import org.junit.Test

class DogBreedsViewStateMapperTest {

    private companion object {
        val SOME_DOG_BREED_ENTITIES = listOf(
            DogBreedEntity(name = "Pug", breed = "pug", subBreed = null),
            DogBreedEntity(name = "Golden Retriever", breed = "retriever", subBreed = "golden")
        )
    }

    private val subject = DogBreedsViewStateMapper()

    @Test
    fun givenMapper_whenMapWithSuccessResult_thenOutputMatches() {
        val result = subject.map(DataResult.Success(SOME_DOG_BREED_ENTITIES))
        Truth.assertThat(result).isInstanceOf(DogBreedsViewState.Ready::class.java)
        val readyResult = result as DogBreedsViewState.Ready
        SOME_DOG_BREED_ENTITIES.mapIndexed { index, entity ->
            Truth.assertThat(readyResult.breeds[index].breedName).isEqualTo(entity.name)
        }
    }

    @Test
    fun givenMapper_whenMapWithErrorResult_thenOutputMatches() {
        val result = subject.map(DataResult.Error("Error"))
        Truth.assertThat(result).isInstanceOf(DogBreedsViewState.Error::class.java)
    }

    @Test
    fun givenMapper_whenMapEntitiesWithSuccessResult_thenOutputMatches() {
        val result = subject.mapToEntities(DataResult.Success(SOME_DOG_BREED_ENTITIES))
        Truth.assertThat(result).isEqualTo(SOME_DOG_BREED_ENTITIES)
    }

    @Test
    fun givenMapper_whenMapEntitiesWithErrorResult_thenOutputIsEmpty() {
        val result = subject.mapToEntities(DataResult.Error("Error"))
        Truth.assertThat(result).isEmpty()
    }

}