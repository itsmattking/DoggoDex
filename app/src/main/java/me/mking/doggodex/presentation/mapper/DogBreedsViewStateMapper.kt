package me.mking.doggodex.presentation.mapper

import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.presentation.viewstate.BrowseViewState
import me.mking.doggodex.presentation.viewstate.DogBreedsViewData
import javax.inject.Inject

class DogBreedsViewStateMapper @Inject constructor() {
    fun map(result: DataResult<List<DogBreedEntity>>): BrowseViewState {
        return when(result) {
            is DataResult.Error -> BrowseViewState.Error
            is DataResult.Success -> BrowseViewState.Ready(
                breeds = result.data.map {
                    DogBreedsViewData(
                        breedName = it.name
                    )
                }
            )
        }
    }

    fun mapToEntities(result: DataResult<List<DogBreedEntity>>): List<DogBreedEntity> {
        return when(result) {
            is DataResult.Success -> result.data
            else -> emptyList()
        }
    }
}