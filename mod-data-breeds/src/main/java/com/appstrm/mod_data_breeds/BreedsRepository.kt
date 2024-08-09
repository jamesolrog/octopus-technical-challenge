package com.appstrm.mod_data_breeds

import com.appstrm.cattechnicalchallenge.data.breeds.models.CatBreed
import com.appstrm.cattechnicalchallenge.data.breeds.models.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedsRepository @Inject constructor(
    private val breedsRemoteDataSource: BreedsRemoteDataSource,
) {
    // TODO: Could use room etc for proper cache?
    // This will survive as long as BreedsRepository is in memory
    val breedCache = mutableMapOf<String, CatBreed>()
    val breedImagesCache = mutableMapOf<String, List<Image>>()

    suspend fun getBreeds() = breedCache.values.toList().ifEmpty { getBreedsFromRemote() }
    suspend fun getBreed(breedId: String) = breedCache.getOrElse(breedId) { breedsRemoteDataSource.getBreed(breedId) }
    suspend fun getBreedImages(breedId: String) = breedImagesCache.getOrElse(breedId) { getImagesFromRemote(breedId) }

    private suspend fun getBreedsFromRemote() = breedsRemoteDataSource.getBreeds().also { breedCache.putAll(it.associateBy { it.id }) }
    private suspend fun getImagesFromRemote(breedId: String) = breedsRemoteDataSource.getBreedImages(breedId).also { breedImagesCache.put(breedId, it) }
}