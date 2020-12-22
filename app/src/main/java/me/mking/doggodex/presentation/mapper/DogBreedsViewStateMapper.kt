package me.mking.doggodex.presentation.mapper

import me.mking.doggodex.common.data.DataResult
import me.mking.doggodex.domain.entities.DogBreedEntity
import me.mking.doggodex.presentation.viewstate.DogBreedsViewState
import me.mking.doggodex.presentation.viewstate.DogBreedsViewData
import javax.inject.Inject

class DogBreedsViewStateMapper @Inject constructor() {
    fun map(result: DataResult<List<DogBreedEntity>>): DogBreedsViewState {
        return when(result) {
            is DataResult.Error -> DogBreedsViewState.Error
            is DataResult.Success -> DogBreedsViewState.Ready(
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