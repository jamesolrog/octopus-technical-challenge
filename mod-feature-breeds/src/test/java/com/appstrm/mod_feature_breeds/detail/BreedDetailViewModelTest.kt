package com.appstrm.mod_feature_breeds.detail

import androidx.lifecycle.SavedStateHandle
import com.appstrm.cattechnicalchallenge.data.breeds.models.ExampleCatBreed
import com.appstrm.cattechnicalchallenge.data.breeds.models.Image
import com.appstrm.mod_data_breeds.GetBreedImagesUseCase
import com.appstrm.mod_data_breeds.GetBreedUseCase
import com.example.mod_utils_test.MainDispatcherRule
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.stub

private const val FakeBreedId = "FakeBreedId"
private val fakeBreed = ExampleCatBreed
private val fakeImages = listOf(
    Image(
        id = "test",
        width = 100,
        height = 100,
        url = "test",
    )
)

class BreedDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockGetBreedUseCase = mock<GetBreedUseCase>()
    private val mockGetBreedImagesUseCase = mock<GetBreedImagesUseCase>()
    private val mockSavedStateHandle = mock<SavedStateHandle>()

    private val viewModel by lazy {
        BreedDetailViewModel(mockGetBreedUseCase, mockGetBreedImagesUseCase, mockSavedStateHandle)
    }

    @Before
    fun setUp() {
        mockSavedStateHandle.stub {
            on { get<String>(any()) } doReturn FakeBreedId
        }
    }

    @Test
    fun `When the viewModel inits, Then the loading ui state is correct`() = runTest {
        mockGetBreedUseCase.stub {
            onBlocking { getBreed(any()) } doSuspendableAnswer {
                // Delay call to ensure loading state has time to assert
                awaitCancellation()
            }
        }
        mockGetBreedImagesUseCase.stub {
            onBlocking { getBreedImages(any()) } doSuspendableAnswer {
                // Delay call to ensure loading state has time to assert
                awaitCancellation()
            }
        }
        val expectedUiState = BreedDetailUiState(
            breedSummary = CatBreedSummary(),
            imagesState = BreedImagesState.Loading,
        )
        assertEquals(expectedUiState, viewModel.uiState.value)
    }

    @Test
    fun `Given the call to get a breed is successful, When the call is complete, Then the ui state is updated to contain breed information`() =
        runTest {
            val expectedUiState = BreedDetailUiState(
                breedSummary = CatBreedSummary(
                    id = ExampleCatBreed.id,
                    name = ExampleCatBreed.name,
                    weight = ExampleCatBreed.weight,
                    temperament = ExampleCatBreed.temperament,
                    origin = ExampleCatBreed.origin,
                    description = ExampleCatBreed.description,
                    lifeSpan = ExampleCatBreed.lifeSpan,
                    intelligence = ExampleCatBreed.intelligence,
                    childFriendly = ExampleCatBreed.childFriendly,
                ),
                imagesState = BreedImagesState.Ready(fakeImages),
            )
            mockGetBreedUseCase.stub {
                onBlocking { getBreed(FakeBreedId) } doReturn fakeBreed
            }
            mockGetBreedImagesUseCase.stub {
                onBlocking { getBreedImages(any()) } doReturn fakeImages
            }

            assertEquals(expectedUiState, viewModel.uiState.value)
        }

    @Test
    fun `Given the call to get a breed fails, When call is complete, Then the ui state is updated to contain an empty breed`() =
        runTest {
            val errorMessage = "There was an error."
            mockGetBreedUseCase.stub {
                onBlocking { getBreed(FakeBreedId) } doAnswer { throw Exception(errorMessage) }
            }
            mockGetBreedImagesUseCase.stub {
                onBlocking { getBreedImages(any()) } doReturn fakeImages
            }

            val expectedUiState = BreedDetailUiState(
                breedSummary = CatBreedSummary(),
                imagesState = BreedImagesState.Ready(fakeImages),
            )

            assertEquals(expectedUiState, viewModel.uiState.value)
        }

    @Test
    fun `Given the call to get breed images fails, When call is complete, Then the ui state is updated to show an error message for the image section`() =
        runTest {
            val errorMessage = "There was an error."
            mockGetBreedUseCase.stub {
                onBlocking { getBreed(FakeBreedId) } doReturn fakeBreed
            }
            mockGetBreedImagesUseCase.stub {
                onBlocking { getBreedImages(any()) } doAnswer { throw Exception(errorMessage) }
            }

            val expectedUiState = BreedDetailUiState(
                breedSummary = CatBreedSummary(
                    id = ExampleCatBreed.id,
                    name = ExampleCatBreed.name,
                    weight = ExampleCatBreed.weight,
                    temperament = ExampleCatBreed.temperament,
                    origin = ExampleCatBreed.origin,
                    description = ExampleCatBreed.description,
                    lifeSpan = ExampleCatBreed.lifeSpan,
                    intelligence = ExampleCatBreed.intelligence,
                    childFriendly = ExampleCatBreed.childFriendly,
                ),
                imagesState = BreedImagesState.Error(errorMessage),
            )

            assertEquals(expectedUiState, viewModel.uiState.value)
        }
}