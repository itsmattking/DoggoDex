package me.mking.doggodex

import android.app.Activity
import com.google.common.truth.Truth
import io.mockk.*
import me.mking.doggodex.presentation.viewstate.DogBreedImagesViewState
import me.mking.doggodex.ui.browse.Direction
import me.mking.doggodex.ui.browse.DogBreedImagesRecyclerAdapter
import me.mking.doggodex.ui.browse.DogBreedImagesRecyclerView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class DogBreedImagesRecyclerViewTest {

    private companion object {
        val SOME_DOG_BREED_IMAGES = listOf(
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_13909.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_2522.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_5521.jpg"
        ).map { DogBreedImagesViewState.DogBreedImageViewData(it) }
    }

    private lateinit var activityController: ActivityController<Activity>
    private lateinit var activity: Activity

    private lateinit var dogBreedImagesRecyclerView: DogBreedImagesRecyclerView

    private val mockPreLoadingRecyclerAdapter: DogBreedImagesRecyclerAdapter = spyk(
        DogBreedImagesRecyclerAdapter(SOME_DOG_BREED_IMAGES)
    )

    @Before
    fun setUp() {
        activityController = Robolectric.buildActivity(Activity::class.java)
        activity = activityController.get()
        dogBreedImagesRecyclerView = DogBreedImagesRecyclerView(activity)
        activity.setContentView(dogBreedImagesRecyclerView);
        dogBreedImagesRecyclerView.adapter = mockPreLoadingRecyclerAdapter
        activityController.create().start().visible()
    }

    @Test
    fun givenView_whenPreLoadingRecyclerAdapterAdded_thenPreloadIsCalled() {
        verify {
            mockPreLoadingRecyclerAdapter.preload(any(), 0, Direction.Forward)
        }
    }

    @Test
    fun givenView_whenRecyclerViewScrolled_thenPreloadIsCalled() {
        dogBreedImagesRecyclerView.smoothScrollToPosition(1)
        Robolectric.flushForegroundThreadScheduler()
        verifyOrder {
            mockPreLoadingRecyclerAdapter.preload(any(), 0, Direction.Forward)
            mockPreLoadingRecyclerAdapter.preload(any(), 1, Direction.Forward)
        }
    }

    @Test
    fun givenView_whenRecyclerViewScrolledBack_thenPreloadIsCalled() {
        dogBreedImagesRecyclerView.smoothScrollToPosition(1)
        Robolectric.flushForegroundThreadScheduler()
        dogBreedImagesRecyclerView.smoothScrollToPosition(0)
        Robolectric.flushForegroundThreadScheduler()
        verifyOrder {
            mockPreLoadingRecyclerAdapter.preload(any(), 0, Direction.Forward)
            mockPreLoadingRecyclerAdapter.preload(any(), 1, Direction.Forward)
            mockPreLoadingRecyclerAdapter.preload(any(), 0, Direction.Backward)
        }
    }

}