package petros.efthymiou.groovy.playlists

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.playlist.*
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistRepositoryShould : BaseUnitTest() {
    private val service: PlaylistService = mock()
    private val mapper: PlaylistMapper = mock()
    private val playlists = mock<List<Playlist>>()
    private val playlistRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("There was an error")

    @Test
    fun getPlaylistFromService() = runTest {
        val repository = PlaylistRepository(service, mapper)

        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitMappedPlaylistsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()

        assertEquals(
            playlists,
            repository
                .getPlaylists()
                .first()
                .getOrNull()
        )
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailureCase()
        assertEquals(
            exception,
            repository
                .getPlaylists()
                .first()
                .exceptionOrNull()
        )
    }

    @Test
    fun delegateBusinessLogicToMapper() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.getPlaylists().first()
        verify(mapper, times(1)).invoke(playlistRaw)
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(flow {
            emit(Result.failure(exception))
        })

        whenever(mapper.invoke(playlistRaw)).thenReturn(playlists)

        return PlaylistRepository(service, mapper)
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(flow {
            emit(Result.success(playlistRaw))
        })

        whenever(mapper.invoke(playlistRaw)).thenReturn(playlists)

        return PlaylistRepository(service, mapper)
    }
}