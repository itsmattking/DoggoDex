package me.mking.doggodex

import com.google.common.truth.Truth
import me.mking.doggodex.data.mappers.DogBreedImagesResponseMapper
import me.mking.doggodex.data.models.DogBreedImagesResponse
import me.mking.doggodex.domain.entities.DogBreedImageEntity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DogBreedImagesResponseMapperTest {

    private companion object {
        val SOME_DOG_BREED_IMAGES_RESPONSE = DogBreedImagesResponse(
            message = listOf(
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_13909.jpg",
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_2522.jpg",
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_5521.jpg"
            ),
            status = ""
        )
    }

    private val subject: DogBreedImagesResponseMapper = DogBreedImagesResponseMapper()

    @Test
    fun givenDogBreedImagesResponseMapper_whenMap_thenResultMatches() {
        val result = subject.map(SOME_DOG_BREED_IMAGES_RESPONSE)
        Truth.assertThat(result.map { it.url }).containsExactlyElementsIn(
            SOME_DOG_BREED_IMAGES_RESPONSE.message
        )
    }
}