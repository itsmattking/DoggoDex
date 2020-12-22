package me.mking.doggodex.presentation.viewstate

sealed class DogBreedsViewState {
    object Loading : DogBreedsViewState()
    object Error : DogBreedsViewState()
    data class Ready(
        val breeds: List<DogBreedsViewData>
    ) : DogBreedsViewState()

}