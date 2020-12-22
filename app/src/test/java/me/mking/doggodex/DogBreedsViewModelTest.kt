package me.mking.doggodex

import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.domain.usecases.GetDogBreedsUseCase
import me.mking.doggodex.presentation.mapper.DogBreedsViewStateMapper
import me.mking.doggodex.presentation.viewmodel.DogBreedsViewModel
import me.mking.doggodex.presentation.viewstate.DogBreedsNavigation
import me.mking.doggodex.presentation.viewstate.DogBreedsViewState
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class DogBreedsViewModelTest {

    private companion object {
        val SOME_DOG_BREED_ENTITIES = listOf(
            DogBreedEntity(name = "Pug", breed = "pug", subBreed = null),
            DogBreedEntity(name = "Golden Retriever", breed = "retriever", subBreed = "golden")
        )
    }

    private val dogBreedsViewStateObserver: Observer<DogBreedsViewState> = mockk {
        every { onChanged(any()) } returns Unit
    }

    private val mockGetDogBreedsUseCase: GetDogBreedsUseCase = mockk {
        coEvery { execute() }.returns(DataResult.Success(emptyList()))
    }

    private val mockDogBreedsViewStateMapper: DogBreedsViewStateMapper = mockk {
        every { map(any()) } returns DogBreedsViewState.Ready(breeds = emptyList())
        every { mapToEntities(any()) } returns emptyList()
    }

    @Test
    fun givenViewModel_whenLoadDogBreeds_thenStateIsLoadingThenReady() = runBlockingTest {
        val subject = givenSubject()
        subject.loadDogBreeds()
        coVerify { mockGetDogBreedsUseCase.execute() }
        verifyOrder {
            dogBreedsViewStateObserver.onChanged(DogBreedsViewState.Loading)
            dogBreedsViewStateObserver.onChanged(withArg {
                Truth.assertThat(it).isInstanceOf(DogBreedsViewState.Ready::class.java)
            })
        }
    }

    @Test
    fun givenViewModelAndMapperReturnsEntities_whenLoadDogBreedsTwice_thenStateDoesNotUpdate() =
        runBlockingTest {
            givenMapperReturnsEntities(listOf(mockk()))
            val subject = givenSubject()
            subject.loadDogBreeds()
            subject.loadDogBreeds()
            coVerify(exactly = 1) { mockGetDogBreedsUseCase.execute() }
            coVerify(exactly = 1) { mockDogBreedsViewStateMapper.map(any()) }
            coVerify(exactly = 1) { mockDogBreedsViewStateMapper.mapToEntities(any()) }
            verify(exactly = 2) { dogBreedsViewStateObserver.onChanged(any()) }
        }

    @Test
    fun givenViewModelAndMapperReturnsNoEntities_whenLoadDogBreedsTwice_thenStateStillUpdates() =
        runBlockingTest {
            givenMapperReturnsEntities()
            val subject = givenSubject()
            subject.loadDogBreeds()
            subject.loadDogBreeds()
            coVerify(exactly = 2) { mockGetDogBreedsUseCase.execute() }
            coVerify(exactly = 2) { mockDogBreedsViewStateMapper.map(any()) }
            coVerify(exactly = 2) { mockDogBreedsViewStateMapper.mapToEntities(any()) }
            verify(exactly = 4) { dogBreedsViewStateObserver.onChanged(any()) }
        }

    @Test
    fun givenViewModelAndMapperReturnsEntities_whenOnDogBreedClickWithValidPosition_thenNavigationMatches() =
        runBlockingTest {
            givenMapperReturnsEntities(SOME_DOG_BREED_ENTITIES)
            val subject = givenSubject()
            subject.loadDogBreeds()
            subject.onDogBreedClicked(1)
            val navigationResult = (subject.navigation.value as DogBreedsNavigation.ToBreedImages)
            Truth.assertThat(navigationResult).isNotNull()
            Truth.assertThat(navigationResult.dogBreedInput.name)
                .isEqualTo(SOME_DOG_BREED_ENTITIES[1].name)
            Truth.assertThat(navigationResult.dogBreedInput.breed)
                .isEqualTo(SOME_DOG_BREED_ENTITIES[1].breed)
            Truth.assertThat(navigationResult.dogBreedInput.subBreed)
                .isEqualTo(SOME_DOG_BREED_ENTITIES[1].subBreed)
        }

    @Test
    fun givenViewModelAndMapperReturnsEntities_whenOnDogBreedClickWithInvalidPosition_thenNavigationIsNull() =
        runBlockingTest {
            givenMapperReturnsEntities(SOME_DOG_BREED_ENTITIES)
            val subject = givenSubject()
            subject.loadDogBreeds()
            subject.onDogBreedClicked(99)
            Truth.assertThat(subject.navigation.value).isNull()
        }

    private fun givenSubject(): DogBreedsViewModel {
        val subject = DogBreedsViewModel(mockGetDogBreedsUseCase, mockDogBreedsViewStateMapper)
        subject.state.observeForever(dogBreedsViewStateObserver)
        return subject
    }

    private fun givenMapperReturnsEntities(list: List<DogBreedEntity> = emptyList()) {
        every { mockDogBreedsViewStateMapper.mapToEntities(any()) } returns list
    }

}