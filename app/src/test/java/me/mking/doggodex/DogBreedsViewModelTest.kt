package me.mking.doggodex

import android.os.Build
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.domain.usecases.GetDogBreedsUseCase
import me.mking.doggodex.presentation.mapper.DogBreedsViewStateMapper
import me.mking.doggodex.presentation.viewmodel.DogBreedsViewModel
import me.mking.doggodex.presentation.viewstate.BrowseViewState
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class DogBreedsViewModelTest {

    private val browseViewStateObserver: Observer<BrowseViewState> = mockk {
        every { onChanged(any()) } returns Unit
    }

    private val mockGetDogBreedsUseCase: GetDogBreedsUseCase = mockk {
        coEvery { execute() }.returns(DataResult.Success(emptyList()))
    }

    private val mockDogBreedsViewStateMapper: DogBreedsViewStateMapper = mockk {
        every { map(any()) } returns BrowseViewState.Ready(breeds = emptyList())
        every { mapToEntities(any()) } returns emptyList()
    }

    @Test
    fun givenViewModel_whenLoadDogBreeds_thenStateIsLoadingThenReady() = runBlockingTest {
        val subject = givenSubject()
        subject.loadDogBreeds()
        coVerify { mockGetDogBreedsUseCase.execute() }
        verifyOrder {
            browseViewStateObserver.onChanged(BrowseViewState.Loading)
            browseViewStateObserver.onChanged(withArg {
                Truth.assertThat(it).isInstanceOf(BrowseViewState.Ready::class.java)
            })
        }
    }

    private fun givenSubject(): DogBreedsViewModel {
        val subject = DogBreedsViewModel(mockGetDogBreedsUseCase, mockDogBreedsViewStateMapper)
        subject.state.observeForever(browseViewStateObserver)
        return subject
    }

}