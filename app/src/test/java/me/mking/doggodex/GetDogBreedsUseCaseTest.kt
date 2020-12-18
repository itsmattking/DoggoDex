package me.mking.doggodex

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.repositories.DogBreedRepository
import me.mking.doggodex.domain.usecases.GetDogBreedsUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetDogBreedsUseCaseTest {

    private companion object {
        val SOME_DOG_BREED_LIST = listOf(
            DogBreedEntity(name = "American Terrier", "terrier", "american"),
            DogBreedEntity(name = "Australian Terrier", "terrier", "australian"),
            DogBreedEntity(name = "Bedlington Terrier", "terrier", "bedlington"),
            DogBreedEntity(name = "Boston Bulldog", "bulldog", "boston"),
            DogBreedEntity(name = "English Bulldog", "bulldog", "english"),
            DogBreedEntity(name = "French Bulldog", "bulldog", "french"),
            DogBreedEntity(name = "Golden Retriever", "retriever", "golden"),
            DogBreedEntity(name = "Pug", "pug"),
            DogBreedEntity(name = "Stbernard", "stbernard")
        )
    }

    private val mockDogBreedsRepository: DogBreedRepository = mockk {
        coEvery { getDogBreeds() }.returns(SOME_DOG_BREED_LIST)
    }
    private val subject = GetDogBreedsUseCase(mockDogBreedsRepository)

    @Test
    fun testGivenGetDogBreedsUseCase_whenExecute_thenDataIsReturned() = runBlocking {
        val result = subject.execute()
        Truth.assertThat(result).isInstanceOf(DataResult.Success::class.java)
    }
}