package me.mking.doggodex.presentation.viewstate

sealed class DogBreedImagesViewState {
    object Error : DogBreedImagesViewState()
    object Loading : DogBreedImagesViewState()
    data class Ready(
        val dogBreedName: String,
        val dogBreedImages: List<DogBreedImageViewData>
    ) : DogBreedImagesViewState()
    data class DogBreedImageViewData(
        val url: String
    )
}
