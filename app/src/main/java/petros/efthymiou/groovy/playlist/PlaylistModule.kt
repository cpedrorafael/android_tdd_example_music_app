package petros.efthymiou.groovy.playlist

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {
    @Provides
    fun playListAPI(retrofit: Retrofit) = retrofit.create(PlayListAPI::class.java)

    @Provides
    fun retrofit()  =  Retrofit.Builder()
        .baseUrl("http://10.0.0.2:3000")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}