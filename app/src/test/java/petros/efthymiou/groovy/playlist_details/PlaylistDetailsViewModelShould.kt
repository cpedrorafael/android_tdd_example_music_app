package petros.efthymiou.groovy.playlist_details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.playlist.PlaylistDetails
import petros.efthymiou.groovy.playlist.PlaylistDetailsViewModel
import petros.efthymiou.groovy.playlist.PlaylistRepository
import petros.efthymiou.groovy.playlist.PlaylistViewModel
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest

class PlaylistDetailsViewModelShould : BaseUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlistDetails = mock<PlaylistDetails>()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("There was an error")

    private fun mockSuccessfulCase(): PlaylistDetailsViewModel {
        runBlocking {
            whenever(repository.getPlaylistDetails()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        return PlaylistDetailsViewModel(repository)
    }

    private fun mockErrorCase(): PlaylistDetailsViewModel {
        runBlocking {
            whenever(repository.getPlaylistDetails()).thenReturn(flow {
                emit(Result.failure(exception))
            })
        }

        return PlaylistDetailsViewModel(repository)
    }

    @Test
    fun callPlaylistDetailsFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.playlistDetails.getValueForTest()

        verify(repository, times(1)).getPlaylistDetails()
    }


    @Test
    fun emitsPlaylistDetailsFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitsErrorWhenReceiveError() {
        val viewModel = mockErrorCase()

        assertEquals(exception, viewModel.playlistDetails.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun showSpinnerWhenLoading() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues{
            viewModel.playlistDetails.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeSpinnerAfterDetailsLoaded() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlistDetails.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeSpinnerAfterError(){
        val viewModel = mockErrorCase()

        viewModel.loader.captureValues {
            viewModel.playlistDetails.getValueForTest()

            assertEquals(false, values.last())
        }
    }
}