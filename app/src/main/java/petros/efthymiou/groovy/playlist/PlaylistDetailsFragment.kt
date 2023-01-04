package petros.efthymiou.groovy.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist_details.*
import petros.efthymiou.groovy.R
import javax.inject.Inject

private const val ARG_PLAYLIST_DETAILS_ID = "param1"

@AndroidEntryPoint
class PlaylistDetailsFragment : Fragment() {

    private var detailsId: Int = -1

    lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            detailsId = it.getInt(ARG_PLAYLIST_DETAILS_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_details, container, false)
        setupViewModel()

        viewModel.playlistDetails.observe(this as LifecycleOwner){ result ->
            if(result.isSuccess){
                result.getOrNull()?.let { setupDetailsView(it) }
            }
        }
        return view
    }

    private fun setupDetailsView(details: PlaylistDetails) {
        val nameView = playlist_details_name
        nameView.text = details.name

        val detailsView = playlist_details_details
        detailsView.text = details.details
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistDetailsViewModel::class.java]
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            PlaylistDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PLAYLIST_DETAILS_ID, param1)
                }
            }
    }
}