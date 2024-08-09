package com.appstrm.mod_data_breeds

import javax.inject.Inject

class GetBreedUseCase @Inject constructor(
    private val breedsRepository: BreedsRepository
) {
    suspend fun getBreed(breedId: String) = breedsRepository.getBreed(breedId)
}