package ru.igor.rodin.retrofit.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponseData(@Json(name = "results") val results: List<UserData>)

@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "name") val name: NameData,
    @Json(name = "location") val location: LocationData,
    @Json(name = "email") val email: String,
    @Json(name = "cell") val phone: String,
    @Json(name = "picture") val avatar: PictureData,
) {
    @JsonClass(generateAdapter = true)
    data class NameData(
        @Json(name = "first") val firstName: String,
        @Json(name = "last") val lastName: String
    )

    @JsonClass(generateAdapter = true)
    data class LocationData(@Json(name = "country") val country: String)

    @JsonClass(generateAdapter = true)
    data class PictureData(@Json(name = "large") val picture: String)
}
