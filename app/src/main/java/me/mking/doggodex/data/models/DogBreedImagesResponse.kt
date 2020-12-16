package me.mking.doggodex.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DogBreedImagesResponse(
    @field:Json(name = "message")
    val message: List<String>,
    @field:Json(name = "status")
    val status: String
)