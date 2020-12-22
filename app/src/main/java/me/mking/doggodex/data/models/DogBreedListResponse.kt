package me.mking.doggodex.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DogBreedListResponse(
    @field:Json(name = "message")
    val message: Map<String, List<String>>,
    @field:Json(name = "status")
    val status: String
) {
    companion object {
        const val STATUS_SUCCESS = "success"
    }
}