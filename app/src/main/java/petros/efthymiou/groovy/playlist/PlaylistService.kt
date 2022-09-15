package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.playlists.PlayListAPI
import java.lang.RuntimeException

class PlaylistService(private val playListAPI: PlayListAPI) {
    suspend fun fetchPlaylists(): Flow<Result<List<Playlist>>> {
        return flow {
            emit(Result.success(playListAPI.getPlaylists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}
