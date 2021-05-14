package com.katherineryn.motv3.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.katherineryn.motv3.R
import com.katherineryn.motv3.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {
    private val dummySize = 3

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovies() {
        // check is rv_movie displayed
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        // try to scroll
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummySize))
    }

    @Test
    fun loadDetailMovie() {
        // check is rv_movie displayed
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        // click first item
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        // check if text view is displayed
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShows() {
        // click menu with id navigation_tvshow
        onView(withId(R.id.navigation_tvshow)).perform(click())
        // check is rv_tvshow displayed
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        // try to scroll
        onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummySize))
    }

    @Test
    fun loadDetailTvShow() {
        // click menu with id navigation_tvshow
        onView(withId(R.id.navigation_tvshow)).perform(click())
        // check is rv_tvshow displayed
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        // click first item
        onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        // check if text view is displayed
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavMovies() {
        // click menu with id navigation_favorite
        onView(withId(R.id.navigation_favorite)).perform(click())
        // check if rv_fav_movie is displayed
        onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed()))
        // try to scroll
        onView(withId(R.id.rv_fav_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummySize))
    }

    @Test
    fun loadDetailFavMovie() {
        // 1. add movie to favorite
        // check is rv_movie displayed
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        // click first item
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        // add item[0] to favorite
        onView(withId(R.id.fab_favorite)).perform(click())
        // back to main menu
        onView(isRoot()).perform(ViewActions.pressBack())

        // 2. check the data in fav
        // click menu with id navigation_favorite
        onView(withId(R.id.navigation_favorite)).perform(click())
        // click first item
        onView(withId(R.id.rv_fav_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        // check if text view is displayed
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        // undo favorite
        onView(withId(R.id.fab_favorite)).perform(click())
    }

    @Test
    fun loadFavTvShows() {
        // click menu with id navigation_favorite
        onView(withId(R.id.navigation_favorite)).perform(click())
        // move to tv show section
        onView(withText("TV SHOW")).perform(click())
        // check if rv_fav_tvshow is displayed
        onView(withId(R.id.rv_fav_tvshow)).check(matches(isDisplayed()))
        // try to scroll
        onView(withId(R.id.rv_fav_tvshow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummySize))
    }

    @Test
    fun loadDetailFavTvShow() {
        // 1. add movie to favorite
        // click menu with id navigation_tvshow
        onView(withId(R.id.navigation_tvshow)).perform(click())
        // check is rv_tvshow displayed
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        // click first item
        onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        // add item[0] to favorite
        onView(withId(R.id.fab_favorite)).perform(click())
        // back to main menu
        onView(isRoot()).perform(ViewActions.pressBack())

        // 2. check the data in fav
        // click menu with id navigation_favorite
        onView(withId(R.id.navigation_favorite)).perform(click())
        // move to tv show section
        onView(withText("TV SHOW")).perform(click())
        // click first item
        onView(withId(R.id.rv_fav_tvshow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        // check if text view is displayed
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        // undo favorite
        onView(withId(R.id.fab_favorite)).perform(click())
    }
}