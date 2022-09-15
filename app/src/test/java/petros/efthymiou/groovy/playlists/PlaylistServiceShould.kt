package petros.efthymiou.groovy.playlists

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.playlist.Playlist
import petros.efthymiou.groovy.playlist.PlaylistService
import petros.efthymiou.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

class PlaylistServiceShould : BaseUnitTest() {
    private lateinit var service : PlaylistService
    private val playlistAPI : PlayListAPI = mock()
    private val playlists = mock<List<Playlist>>()

    @Test
    fun getPlaylistFromAPI() = runBlockingTest {
        service = PlaylistService(playlistAPI)

        //calling first() will force the flow to emit
        service.fetchPlaylists().first()

        verify(playlistAPI, times(1)).getPlaylists()
    }

    @Test
    fun emitPlaylistFromService() = runBlockingTest {
        service = PlaylistService(playlistAPI)

        whenever(playlistAPI.getPlaylists())
            .thenReturn(playlists)

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest{
        service = PlaylistService(playlistAPI)

        whenever(playlistAPI.getPlaylists())
            .thenThrow(RuntimeException("Not found"))

        assertEquals("Something went wrong", service.fetchPlaylists().first().exceptionOrNull()!!.message)
    }
}