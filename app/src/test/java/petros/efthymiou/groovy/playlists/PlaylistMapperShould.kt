package petros.efthymiou.groovy.playlists

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.playlist.PlaylistMapper
import petros.efthymiou.groovy.playlist.PlaylistRaw
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.R

class PlaylistMapperShould : BaseUnitTest() {
    private val playlistRaw = PlaylistRaw("1", "name", "category")
    private val playlistRawRock = PlaylistRaw("1", "name", "rock")
    private val mapper = PlaylistMapper()
    private val playlists = mapper.invoke(listOf(playlistRaw))
    private val playlist = playlists.first()
    private val playlistRock = mapper(listOf(playlistRawRock)).first()

    @Test
    fun keepSameId() = runBlockingTest {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName() = runBlockingTest {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory() = runBlockingTest {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock(){
        assertEquals(R.mipmap.playlist, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory() {
        assertEquals(R.mipmap.rock, playlistRock.image)
    }
}