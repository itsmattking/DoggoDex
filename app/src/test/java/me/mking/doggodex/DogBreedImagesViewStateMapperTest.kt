package me.mking.doggodex

import com.google.common.truth.Truth
import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedImageEntity
import me.mking.doggodex.presentation.viewmodel.DogBreedInput
import me.mking.doggodex.presentation.mapper.DogBreedImagesViewStateMapper
import me.mking.doggodex.presentation.viewstate.DogBreedImagesViewState
import org.junit.Test

class DogBreedImagesViewStateMapperTest {
    private companion object {
        val SOME_DOG_BREED_INPUT = DogBreedInput(
            name = "Golden Retriever",
            breed = "retriever",
            subBreed = "golden"
        )
        val SOME_DOG_BREED_IMAGES = listOf(
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_13909.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_2522.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_5521.jpg").map {
                DogBreedImageEntity(url = it)
        }
    }

    private val subject = DogBreedImagesViewStateMapper()

    @Test
    fun givenMapper_whenMapWithSuccess_thenOutputMatches() {
        val result = subject.map(SOME_DOG_BREED_INPUT, DataResult.Success(SOME_DOG_BREED_IMAGES))
        Truth.assertThat(result).isInstanceOf(DogBreedImagesViewState.Ready::class.java)
        val readyResult = result as DogBreedImagesViewState.Ready
        Truth.assertThat(readyResult.dogBreedName).isEqualTo(SOME_DOG_BREED_INPUT.name)
        SOME_DOG_BREED_IMAGES.mapIndexed { index, entity ->
            Truth.assertThat(readyResult.dogBreedImages[index].url).isEqualTo(entity.url)
        }
    }

    @Test
    fun givenMapper_whenMapWithError_thenOutputMatches() {
        val result = subject.map(SOME_DOG_BREED_INPUT, DataResult.Error("Error"))
        Truth.assertThat(result).isInstanceOf(DogBreedImagesViewState.Error::class.java)
    }

}