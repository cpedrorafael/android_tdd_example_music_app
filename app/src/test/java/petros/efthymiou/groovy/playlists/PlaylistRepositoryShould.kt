package petros.efthymiou.groovy.playlists

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.playlist.Playlist
import petros.efthymiou.groovy.playlist.PlaylistRepository
import petros.efthymiou.groovy.playlist.PlaylistService
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistRepositoryShould : BaseUnitTest() {
    private val service : PlaylistService = mock()
    private val playlists = mock<List<Playlist>>()
    @Test
    fun getPlaylistFromService() = runBlockingTest {
        val repository = PlaylistRepository(service)

        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitPlaylistsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()

        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(flow {
            emit(Result.success(playlists))
        })

        val repository = PlaylistRepository(service)
        return repository
    }
}