package me.chkfung.demo

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.chkfung.demo.data.source.CurrencyRepo
import me.chkfung.demo.data.source.FakeCurrencyRepo
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class CurrencyListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: FakeCurrencyRepo

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun currency_displayInUI() {
        launchFragmentInHiltContainer<CurrencyListFragment>(Bundle(), R.style.Theme_Demo)
        onView(withId(R.id.rv_currency))
            .check(
                matches(
                    hasDescendant(withText("BTC"))
                )
            )
    }

    @After
    fun tearDown() {
    }
}