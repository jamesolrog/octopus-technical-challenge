package com.appstrm.mod_data_breeds

class GetBreedsUseCase @javax.inject.Inject constructor(
    private val productsRepository: BreedsRepository
) {
    suspend fun getBreeds() = productsRepository.getBreeds()
}