package ru.igor.rodin.retrofit.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponseData(@Json(name = "results") val results: List<UserData>)

@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "gender") val gender: String,
    @Json(name = "name") val userName: NameData,
    @Json(name = "location") val location: LocationData,
    @Json(name = "email") val email: String,
    @Json(name = "login") val loginData: LoginData,
    @Json(name = "dob") val doData: DobData,
    @Json(name = "registered") val registeredData: RegisteredData,
    @Json(name = "phone") val phoneWork: String,
    @Json(name = "cell") val phone: String,
    @Json(name = "id") val id: IdData,
    @Json(name = "picture") val avatar: PictureData,
    @Json(name = "nat") val nationality: String
) {
    @JsonClass(generateAdapter = true)
    data class NameData(
        @Json(name = "title") val title: String,
        @Json(name = "first") val firstName: String,
        @Json(name = "last") val lastName: String
    )

    @JsonClass(generateAdapter = true)
    data class LocationData(
        @Json(name = "street") val streetData: StreetData,
        @Json(name = "city") val city: String,
        @Json(name = "state") val state: String,
        @Json(name = "postcode") val postcode: String,
        @Json(name = "coordinates") val coordinates: CoordinatesData,
        @Json(name = "timezone") val timezone: TimezoneData,
        @Json(name = "country") val country: String
    )

    @JsonClass(generateAdapter = true)
    data class StreetData(
        @Json(name = "number") val number: Int,
        @Json(name = "name") val name: String
    )

    @JsonClass(generateAdapter = true)
    data class CoordinatesData(
        @Json(name = "latitude") val latitude: String,
        @Json(name = "longitude") val longitude: String
    )

    @JsonClass(generateAdapter = true)
    data class TimezoneData(
        @Json(name = "offset") val offset: String,
        @Json(name = "description") val description: String
    )

    @JsonClass(generateAdapter = true)
    data class LoginData(
        @Json(name = "uuid") val uuid: String,
        @Json(name = "username") val username: String,
        @Json(name = "password") val password: String,
        @Json(name = "salt") val salt: String,
        @Json(name = "md5") val md5: String,
        @Json(name = "sha1") val sha1: String,
        @Json(name = "sha256") val sha256: String
    )

    @JsonClass(generateAdapter = true)
    data class DobData(
        @Json(name = "date") val date: String,
        @Json(name = "age") val age: Int
    )

    @JsonClass(generateAdapter = true)
    data class RegisteredData(
        @Json(name = "date") val date: String,
        @Json(name = "age") val age: Int
    )

    @JsonClass(generateAdapter = true)
    data class IdData(
        @Json(name = "name") val name: String,
        @Json(name = "value") val value: String?
    )

    @JsonClass(generateAdapter = true)
    data class PictureData(
        @Json(name = "large") val picture: String,
        @Json(name = "medium") val medium: String,
        @Json(name = "thumbnail") val thumbnail: String
    )
}



