package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class PlaylistService @Inject constructor (private val playListAPI: PlayListAPI) {
    suspend fun fetchPlaylists(): Flow<Result<List<PlaylistRaw>>> {
        return flow {
            emit(Result.success(playListAPI.getPlaylists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}
