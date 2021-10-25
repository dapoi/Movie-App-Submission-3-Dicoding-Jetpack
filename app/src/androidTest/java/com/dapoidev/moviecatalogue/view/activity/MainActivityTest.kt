package com.dapoidev.moviecatalogue.view.activity

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.dapoidev.moviecatalogue.R
import com.dapoidev.moviecatalogue.data.utils.DataDummy
import com.dapoidev.moviecatalogue.data.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    private val dataDummyMovie = DataDummy.getMovies()
    private val dataDummyTVShow = DataDummy.getTVShows()

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    // movie list
    @Test
    fun loadListMovie() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataDummyMovie.size))
    }

    // tv show list
    @Test
    fun loadListTVShow() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataDummyTVShow.size))
    }

    // detail movie
    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.img_detail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_date)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_desc)).check(matches(isDisplayed()))

        pressBack()
    }

    // detail tv show
    @Test
    fun loadDetailTVShow() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_date)).check(matches(isDisplayed()))

        onView(withId(R.id.img_detail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_desc)).check(matches(isDisplayed()))

        pressBack()
    }

    // list and detail favorite movie
    @Test
    fun loadListFavMovie() {
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.buttonFav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.fab_fav)).perform(click())

        onView(withId(R.id.rv_movies_fav)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies_fav)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_date)).check(matches(isDisplayed()))
        onView(withId(R.id.img_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_desc)).check(matches(isDisplayed()))

        onView(withId(R.id.buttonFav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    // list and detail favorite tv show
    @Test
    fun loadListFavTVShow() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.buttonFav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.fab_fav)).perform(click())

        onView(withText("TV SHOWS FAVORITE")).perform(click())
        onView(withId(R.id.rv_tvshows_fav)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshows_fav)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_date)).check(matches(isDisplayed()))
        onView(withId(R.id.img_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_desc)).check(matches(isDisplayed()))

        onView(withId(R.id.buttonFav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }
}