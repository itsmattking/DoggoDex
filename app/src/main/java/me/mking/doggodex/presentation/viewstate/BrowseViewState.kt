package me.mking.doggodex.presentation.viewstate

sealed class BrowseViewState {
    object Loading : BrowseViewState()
    object Error : BrowseViewState()
    data class Ready(
        val breeds: List<DogBreedViewData>
    ) : BrowseViewState()

}