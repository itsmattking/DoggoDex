package me.mking.doggodex

import io.mockk.*
import kotlinx.coroutines.runBlocking
import me.mking.doggodex.data.datastores.DogBreedsDataStore
import me.mking.doggodex.data.mappers.DogBreedImagesResponseMapper
import me.mking.doggodex.data.mappers.DogBreedListResponseMapper
import me.mking.doggodex.data.models.DogBreedImagesResponse
import me.mking.doggodex.data.models.DogBreedListResponse
import me.mking.doggodex.data.repositories.DogBreedRepositoryImpl
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.repositories.DogBreedRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DogBreedRepositoryImplTest {

    private companion object {
        val SOME_DOG_BREED_LIST_RESPONSE = DogBreedListResponse(
            message = mapOf(
                "australian" to listOf("shepherd"),
                "bulldog" to listOf("boston", "english", "french"),
                "pug" to emptyList()
            ),
            status = "success"
        )
        val SOME_DOG_BREED_IMAGES_RESPONSE = DogBreedImagesResponse(
            message = listOf(
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_13909.jpg",
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_2522.jpg",
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_5521.jpg"
            ),
            status = "success"
        )
        val SOME_DOG_BREED_ENTITY = DogBreedEntity("Pug", "pug")
        val SOME_DOG_BREED_ENTITY_WITH_SUB_BREED =
            DogBreedEntity("Golden Retriever", "retriever", "golden")
        const val SOME_RANDOM_IMAGE_COUNT = 30
    }

    private val mockDogBreedsDataStore: DogBreedsDataStore = mockk {
        coEvery { getAllBreeds() }.returns(SOME_DOG_BREED_LIST_RESPONSE)
        coEvery { getRandomBreedImages(any(), any()) }.returns(SOME_DOG_BREED_IMAGES_RESPONSE)
    }

    private val mockDogBreedListResponseMapper: DogBreedListResponseMapper = mockk {
        every { map(any()) }.returns(emptyList())
    }

    private val mockDogBreedImagesResponseMapper: DogBreedImagesResponseMapper = mockk {
        every { map(any()) }.returns(emptyList())
    }

    private val subject: DogBreedRepository = DogBreedRepositoryImpl(
        mockDogBreedsDataStore,
        mockDogBreedListResponseMapper,
        mockDogBreedImagesResponseMapper
    )

    @Test
    fun givenDogBreedRepository_whenGetAllBreeds_thenDataSourceIsQueriedAndResultIsMapped() =
        runBlocking {
            subject.getDogBreeds()
            coVerify { mockDogBreedsDataStore.getAllBreeds() }
            verify { mockDogBreedListResponseMapper.map(SOME_DOG_BREED_LIST_RESPONSE) }
        }

    @Test
    fun givenDogBreedRepository_whenGetRandomBreedImages_thenDataSourceIsQueriedAndResultIsMapped() =
        runBlocking {
            subject.getDogBreedImages(SOME_DOG_BREED_ENTITY, SOME_RANDOM_IMAGE_COUNT)
            coVerify { mockDogBreedsDataStore.getRandomBreedImages("pug", SOME_RANDOM_IMAGE_COUNT) }
            verify { mockDogBreedImagesResponseMapper.map(SOME_DOG_BREED_IMAGES_RESPONSE) }
        }

    @Test
    fun givenDogBreedRepository_whenGetRandomBreedImagesWithSubBreed_thenDataSourceIsQueriedAndResultIsMapped() =
        runBlocking {
            subject.getDogBreedImages(SOME_DOG_BREED_ENTITY_WITH_SUB_BREED, SOME_RANDOM_IMAGE_COUNT)
            coVerify {
                mockDogBreedsDataStore.getRandomBreedImages(
                    "retriever/golden",
                    SOME_RANDOM_IMAGE_COUNT
                )
            }
            verify { mockDogBreedImagesResponseMapper.map(SOME_DOG_BREED_IMAGES_RESPONSE) }
        }

}