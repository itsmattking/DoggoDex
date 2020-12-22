package me.mking.doggodex

import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.domain.usecases.GetDogBreedImagesUseCase
import me.mking.doggodex.presentation.DogBreedInput
import me.mking.doggodex.presentation.mapper.DogBreedImagesViewStateMapper
import me.mking.doggodex.presentation.viewmodel.DogBreedImagesViewModel
import me.mking.doggodex.presentation.viewstate.DogBreedImagesViewState
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class DogBreedImagesViewModelTest {

    private companion object {
        val SOME_DOG_BREED_INPUT = DogBreedInput(
            name = "Golden Retriever",
            breed = "retriever",
            subBreed = "golden"
        )
    }

    private val dogBreedImagesViewStateObserver: Observer<DogBreedImagesViewState> = mockk {
        every { onChanged(any()) } returns Unit
    }

    private val mockGetDogBreedImagesUseCase: GetDogBreedImagesUseCase = mockk {
        coEvery { execute(any()) } returns DataResult.Success(emptyList())
    }

    private val mockDogBreedImagesViewStateMapper: DogBreedImagesViewStateMapper = mockk {
        every { map(any(), any()) } returns DogBreedImagesViewState.Ready(
            "Golden Retriever",
            listOf(mockk())
        )
    }

    @Test
    fun givenViewModel_whenLoadDogBreedImages_thenStateIsLoadingThenReady() = runBlockingTest {
        val subject = givenSubject()
        subject.loadDogBreedImages(SOME_DOG_BREED_INPUT)
        coVerify {
            mockGetDogBreedImagesUseCase.execute(any())
        }
        verify { mockDogBreedImagesViewStateMapper.map(SOME_DOG_BREED_INPUT, any()) }
        verifyOrder {
            dogBreedImagesViewStateObserver.onChanged(DogBreedImagesViewState.Loading)
            dogBreedImagesViewStateObserver.onChanged(withArg {
                Truth.assertThat(it).isInstanceOf(DogBreedImagesViewState.Ready::class.java)
            })
        }
    }

    @Test
    fun givenViewModel_whenLoadDogBreedImages_thenCorrectArgumentsPassedToUseCase() =
        runBlockingTest {
            val subject = givenSubject()
            subject.loadDogBreedImages(SOME_DOG_BREED_INPUT)
            coVerify {
                mockGetDogBreedImagesUseCase.execute(withArg {
                    Truth.assertThat(it.dogBreedEntity.name).isEqualTo(SOME_DOG_BREED_INPUT.name)
                    Truth.assertThat(it.dogBreedEntity.breed).isEqualTo(SOME_DOG_BREED_INPUT.breed)
                    Truth.assertThat(it.dogBreedEntity.subBreed)
                        .isEqualTo(SOME_DOG_BREED_INPUT.subBreed)
                })
            }
        }

    @Test
    fun givenViewModelAndMapperReturnsList_whenLoadDogBreedImagesTwice_thenLoadHappensOnce() =
        runBlockingTest {
            givenDogBreedImagesViewStateMapperReturns(listOf(mockk()))
            val subject = givenSubject()
            subject.loadDogBreedImages(SOME_DOG_BREED_INPUT)
            subject.loadDogBreedImages(SOME_DOG_BREED_INPUT)
            coVerify(exactly = 1) { mockGetDogBreedImagesUseCase.execute(any()) }
            verify(exactly = 1) { mockDogBreedImagesViewStateMapper.map(SOME_DOG_BREED_INPUT, any()) }
            verify(exactly = 2) { dogBreedImagesViewStateObserver.onChanged(any()) }
        }

    @Test
    fun givenViewModelAndMapperReturnsEmptyList_whenLoadDogBreedImagesTwice_thenLoadRetries() =
        runBlockingTest {
            givenDogBreedImagesViewStateMapperReturns(emptyList())
            val subject = givenSubject()
            subject.loadDogBreedImages(SOME_DOG_BREED_INPUT)
            subject.loadDogBreedImages(SOME_DOG_BREED_INPUT)
            coVerify(exactly = 2) { mockGetDogBreedImagesUseCase.execute(any()) }
            verify(exactly = 2) { mockDogBreedImagesViewStateMapper.map(SOME_DOG_BREED_INPUT, any()) }
            verify(exactly = 4) { dogBreedImagesViewStateObserver.onChanged(any()) }
        }

    private fun givenSubject(): DogBreedImagesViewModel {
        val subject =
            DogBreedImagesViewModel(mockGetDogBreedImagesUseCase, mockDogBreedImagesViewStateMapper)
        subject.state.observeForever(dogBreedImagesViewStateObserver)
        return subject
    }

    private fun givenDogBreedImagesViewStateMapperReturns(list: List<DogBreedImagesViewState.DogBreedImageViewData> = emptyList()) {
        every {
            mockDogBreedImagesViewStateMapper.map(
                any(),
                any()
            )
        } returns DogBreedImagesViewState.Ready(
            "Golden Retriever",
            list
        )
    }

}