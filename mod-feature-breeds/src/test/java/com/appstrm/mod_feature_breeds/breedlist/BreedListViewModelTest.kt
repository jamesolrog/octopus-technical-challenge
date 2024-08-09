package com.appstrm.mod_feature_breeds.breedlist

import com.appstrm.cattechnicalchallenge.data.breeds.models.ExampleCatBreed
import com.appstrm.mod_data_breeds.GetBreedsUseCase
import com.example.mod_utils_test.MainDispatcherRule
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import java.lang.Exception

class BreedListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockGetBreedUseCase = mock<GetBreedsUseCase>()
    private val viewModel by lazy { BreedListViewModel(mockGetBreedUseCase) }

    @Test
    fun `When the viewModel inits, Then the initial state is loading`() = runTest {
        mockGetBreedUseCase.stub {
            onBlocking { getBreeds() } doSuspendableAnswer {
                // Delay the call to ensure loading state has time to assert
                awaitCancellation()
            }
        }
        assertEquals(BreedListUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `Given breeds are available, When the viewModel inits, Then the state is ready and breeds are correctly set`() =
        runTest {
            val mockBreeds = listOf(ExampleCatBreed)
            mockGetBreedUseCase.stub {
                onBlocking { getBreeds() } doReturn mockBreeds
            }
            val expectedUiState = BreedListUiState.Ready(
                breeds = listOf(
                    BreedSummary(
                        id = mockBreeds[0].id,
                        name = mockBreeds[0].name,
                    )
                )
            )
            assertEquals(expectedUiState, viewModel.uiState.value)
        }

    @Test
    fun `Given breeds are not available, When the viewModel inits, Then the state is error and the message is correct`() =
        runTest {
            val errorMessage = "There was an error."
            mockGetBreedUseCase.stub {
                onBlocking { getBreeds() } doAnswer { throw Exception(errorMessage) }
            }
            assertEquals(BreedListUiState.Error(errorMessage), viewModel.uiState.value)
        }
}