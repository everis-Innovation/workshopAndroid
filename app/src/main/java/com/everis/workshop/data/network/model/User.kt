package com.everis.workshop.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Equivalent of user data class in kotlin
 */
data class User(
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: Name,
    @SerializedName("location") val location: Location,
    @SerializedName("email") val email: String
)

data class Name(
    @SerializedName("title") val title: String,
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
) {
    override fun toString(): String {
        return "$title $first $last"
    }
}

data class Location (
    @SerializedName("street")
    val street: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("coordinates")
    val coordinates: Coordinates,
    @SerializedName("timezone")
    val timezone: TimeZone
    ) {
    fun getAdress(): String {
        return "$street, $city, $state, $postcode"
    }
}

data class Coordinates (
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String
){
    override fun toString(): String {
        return "Lat: $latitude, Long: $longitude"
    }
}

data class TimeZone (
    @SerializedName("offset")
    val offset: String,
    @SerializedName("description")
    val description: String
){
    override fun toString(): String {
        return "offset: $offset, description: $description"
    }
}

data class Info(
    @SerializedName("seed") val seed: String,
    @SerializedName("results") val results: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("version") val version: String
)
/**
 * Entire search result data class
 */
class Result (
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<User>
)