package me.mking.doggodex.presentation.mapper

import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedImageEntity
import me.mking.doggodex.presentation.viewmodel.DogBreedInput
import me.mking.doggodex.presentation.viewstate.DogBreedImagesViewState
import javax.inject.Inject

class DogBreedImagesViewStateMapper @Inject constructor() {
    fun map(
        input: DogBreedInput,
        result: DataResult<List<DogBreedImageEntity>>
    ): DogBreedImagesViewState {
        return when (result) {
            is DataResult.Error -> DogBreedImagesViewState.Error
            is DataResult.Success -> {
                DogBreedImagesViewState.Ready(
                    dogBreedName = input.name,
                    dogBreedImages = result.data.map {
                        DogBreedImagesViewState.DogBreedImageViewData(
                            url = it.url
                        )
                    }
                )
            }
        }
    }
}