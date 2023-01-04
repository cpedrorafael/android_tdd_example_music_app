package petros.efthymiou.groovy.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.onEach

class PlaylistDetailsViewModel(
    private val repository: PlaylistRepository)
    : ViewModel() {
    val loader = MutableLiveData<Boolean>()
    val playlistDetails = liveData {
        loader.postValue(true)
        emitSource(repository.getPlaylistDetails()
            .onEach {
                loader.postValue(false)
            }.asLiveData())
    }
}
