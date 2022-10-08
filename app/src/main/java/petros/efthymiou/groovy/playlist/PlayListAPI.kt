package petros.efthymiou.groovy.playlist

import petros.efthymiou.groovy.playlist.Playlist
import retrofit2.http.GET

interface PlayListAPI {
    @GET("playlists")
    suspend fun getPlaylists() : List<Playlist>
}
