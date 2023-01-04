package petros.efthymiou.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import petros.efthymiou.groovy.playlist.idlingResource

class PlaylistDetailsFeature : BaseUITest() {

    @Before
    fun goToDetailsScreen(){
        onView(
            CoreMatchers.allOf(
                withId(R.id.playlist_image),
                ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))
            )
        ).perform(click())
    }

    @Test
    fun displaysPlaylistNameAndDetails(){
        assertDisplayed(R.id.playlist_details_name)
        assertDisplayed(R.id.playlist_details_details)
    }

    @Test
    fun displaysPlaylistNameAndDetailsExactStrings(){
        assertDisplayed("Hard Rock Cafe")
        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")
    }
}