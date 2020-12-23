package me.mking.doggodex.presentation.viewmodel

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DogBreedInput(
    val name: String,
    val breed: String,
    val subBreed: String?
) : Parcelable