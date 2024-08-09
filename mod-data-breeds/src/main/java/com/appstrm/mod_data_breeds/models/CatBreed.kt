package com.appstrm.cattechnicalchallenge.data.breeds.models

import com.google.gson.annotations.SerializedName

data class CatBreed(
    val weight: Weight,
    val id: String,
    val name: String,
    @SerializedName("cfa_url") val cfaUrl: String? = null,
    @SerializedName("vetstreet_url") val vetstreetUrl: String? = null,
    @SerializedName("vcahospitals_url") val vcahospitalsUrl: String? = null,
    val temperament: String,
    val origin: String,
    @SerializedName("country_codes") val countryCodes: String, // List?
    @SerializedName("country_code") val countryCode: String,
    val description: String,
    @SerializedName("life_span") val lifeSpan: String,
    val indoor: Int,
    val lap: Int? = null,
    @SerializedName("alt_names") val altNames: String,
    val adaptability: Int,
    @SerializedName("affection_level") val affectionLevel: Int,
    @SerializedName("child_friendly") val childFriendly: Int,
    @SerializedName("dog_friendly") val dogFriendly: Int,
    @SerializedName("energy_level") val energyLevel: Int,
    val grooming: Int,
    @SerializedName("health_issues") val healthIssues: Int,
    val intelligence: Int,
    @SerializedName("shedding_level") val sheddingLevel: Int,
    @SerializedName("social_needs") val socialNeeds: Int,
    @SerializedName("stranger_friendly") val strangerFriendly: Int,
    val vocalisation: Int,
    val experimental: Int, // Boolean?
    val hairless: Int, // Boolean?
    val natural: Int, // Boolean?
    val rare: Int, // Boolean?
    val rex: Int, // Boolean?
    @SerializedName("suppressed_tail") val suppressedTail: Int, // Boolean?
    @SerializedName("short_legs") val shortLegs: Int, // Boolean?
    @SerializedName("wikipedia_url") val wikipediaUrl: String? = null,
    val hypoallergenic: Int,
    @SerializedName("reference_image_id") val referenceImageId: String,
    val image: Image? = null
)

data class Weight(
    val imperial: String,
    val metric: String
)

data class Image(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String
)

val ExampleCatBreed = CatBreed(
    weight = Weight(imperial = "7 - 10", metric = "3 - 5"),
    id = "abys",
    name = "Abyssinian",
    cfaUrl = "http://cfa.org/Breeds/BreedsAB/Abyssinian.aspx",
    vetstreetUrl = "http://www.vetstreet.com/cats/abyssinian",
    vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/abyssinian",
    temperament = "Active, Energetic, Independent, Intelligent, Gentle",
    origin = "Egypt",
    countryCodes = "EG",
    countryCode = "EG",
    description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
    lifeSpan = "14 - 15",
    indoor = 0,
    lap = 1,
    altNames = "",
    adaptability = 5,
    affectionLevel = 5,
    childFriendly = 3,
    dogFriendly = 4,
    energyLevel = 5,
    grooming = 1,
    healthIssues = 2,
    intelligence = 5,
    sheddingLevel = 2,
    socialNeeds = 5,
    strangerFriendly = 5,
    vocalisation = 1,
    experimental = 0,
    hairless = 0,
    natural = 1,
    rare = 0,
    rex = 0,
    suppressedTail = 0,
    shortLegs = 0,
    wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
    hypoallergenic = 0,
    referenceImageId = "0XYvRd7oD",
    image = Image(
        id = "0XYvRd7oD",
        width = 1204,
        height = 1445,
        url = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"
    )
)