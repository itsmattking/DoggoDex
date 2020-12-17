package me.mking.doggodex

import android.os.Build
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.mking.doggodex.data.DataResult
import me.mking.doggodex.domain.usecases.GetDogBreedsUseCase
import me.mking.doggodex.presentation.viewmodel.BrowseViewModel
import me.mking.doggodex.presentation.viewstate.BrowseViewState
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class BrowseViewModelTest {

    private val browseViewStateObserver: Observer<BrowseViewState> = mockk {
        every { onChanged(any()) } returns Unit
    }

    private val mockGetDogBreedsUseCase: GetDogBreedsUseCase = mockk {
        coEvery { execute() }.returns(DataResult.Success(emptyList()))
    }

    @Test
    fun givenViewModel_whenLoadDogBreeds_thenStateIsLoading() = runBlockingTest {
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

    private fun givenSubject(): BrowseViewModel {
        val subject = BrowseViewModel(mockGetDogBreedsUseCase)
        subject.state.observeForever(browseViewStateObserver)
        return subject
    }

}