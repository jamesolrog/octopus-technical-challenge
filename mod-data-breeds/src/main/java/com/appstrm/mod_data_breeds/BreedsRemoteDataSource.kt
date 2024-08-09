package com.appstrm.mod_data_breeds

import com.appstrm.cattechnicalchallenge.data.breeds.models.CatBreed
import com.appstrm.cattechnicalchallenge.data.breeds.models.Image
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.jvm.java

typealias BreedsApiResponse = List<CatBreed>

interface BreedsApi {
    @GET("breeds")
    suspend fun getBreeds(): BreedsApiResponse

    @GET("breeds/{breedId}")
    suspend fun getBreed(
        @Path("breedId") breedId: String,
    ): CatBreed

    @GET("images/search")
    suspend fun getBreedImages(
        @Query("limit") limit: Int = 10,
        @Query("breed_ids") breedId: String,
    ): List<Image>
}

class BreedsRemoteDataSource @javax.inject.Inject constructor(retrofit: Retrofit) {
    private val api by lazy { retrofit.create(BreedsApi::class.java) }
    suspend fun getBreeds() = api.getBreeds()
    suspend fun getBreed(breedId: String) = api.getBreed(breedId)
    suspend fun getBreedImages(breedId: String) = api.getBreedImages(breedId = breedId)
}