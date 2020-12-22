package me.mking.doggodex

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.entities.DogBreedImageEntity
import me.mking.doggodex.domain.repositories.DogBreedRepository
import me.mking.doggodex.domain.usecases.GetDogBreedImagesInput
import me.mking.doggodex.domain.usecases.GetDogBreedImagesUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetDogBreedImagesUseCaseTest {
    private companion object {
        val SOME_DOG_BREED_ENTITY = DogBreedEntity(
            name = "Golden Retriever",
            breed = "retriever",
            subBreed = "golden"
        )
        const val SOME_IMAGE_COUNT = 10
        val SOME_GET_DOG_BREED_IMAGES_INPUT = GetDogBreedImagesInput(
            dogBreedEntity = SOME_DOG_BREED_ENTITY,
            imageCount = SOME_IMAGE_COUNT
        )
        val SOME_DOG_BREED_IMAGES = listOf(
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_13909.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_2522.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_5521.jpg"
        ).map {
            DogBreedImageEntity(url = it)
        }
    }

    private val mockDogBreedsRepository: DogBreedRepository = mockk {
        coEvery { getDogBreedImages(any(), any()) } returns SOME_DOG_BREED_IMAGES
    }
    private val subject = GetDogBreedImagesUseCase(mockDogBreedsRepository)

    @Test
    fun givenUseCase_whenExecute_thenResultIsSuccess() = runBlocking {
        val result = subject.execute(SOME_GET_DOG_BREED_IMAGES_INPUT)
        coVerify {
            mockDogBreedsRepository.getDogBreedImages(
                SOME_DOG_BREED_ENTITY,
                SOME_IMAGE_COUNT
            )
        }

        Truth.assertThat(result).isInstanceOf(DataResult.Success::class.java)
        val successResult = result as DataResult.Success
        Truth.assertThat(successResult.data).isEqualTo(SOME_DOG_BREED_IMAGES)
    }

    @Test
    fun givenUseCaseAndRepositoryThrowsException_whenExecute_thenResultIsError() = runBlocking {
        coEvery {
            mockDogBreedsRepository.getDogBreedImages(
                any(),
                any()
            )
        } throws Exception("Some exception")
        val result = subject.execute(SOME_GET_DOG_BREED_IMAGES_INPUT)
        Truth.assertThat(result).isInstanceOf(DataResult.Error::class.java)
    }

}