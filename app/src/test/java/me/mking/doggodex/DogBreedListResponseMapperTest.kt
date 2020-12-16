package me.mking.doggodex

import com.google.common.truth.Truth
import me.mking.doggodex.data.mappers.DogBreedListResponseMapper
import me.mking.doggodex.data.models.DogBreedListResponse
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DogBreedListResponseMapperTest {

    private companion object {
        val SOME_DOG_BREED_LIST_MESSAGE: Map<String, List<String>> = mapOf(
            "bulldog" to listOf("boston", "english", "french"),
            "retriever" to listOf("golden"),
            "pug" to listOf(),
            "terrier" to listOf("american", "australian", "bedlington"),
            "stbernard" to listOf()
        )
        val SOME_ORDERED_DOG_BREED_LIST = listOf(
            "American Terrier",
            "Australian Terrier",
            "Bedlington Terrier",
            "Boston Bulldog",
            "English Bulldog",
            "French Bulldog",
            "Golden Retriever",
            "Pug",
            "Stbernard"
        )
        val SOME_DOG_BREED_LIST_RESPONSE =
            DogBreedListResponse(
                message = SOME_DOG_BREED_LIST_MESSAGE,
                status = ""
            )
    }

    private val subject: DogBreedListResponseMapper = DogBreedListResponseMapper()

    @Test
    fun givenDogBreedListResponse_whenMap_thenObjectsAreCreated() {
        val result = subject.map(SOME_DOG_BREED_LIST_RESPONSE)
        Truth.assertThat(result.map { it.name })
            .containsExactlyElementsIn(SOME_ORDERED_DOG_BREED_LIST)
    }

}