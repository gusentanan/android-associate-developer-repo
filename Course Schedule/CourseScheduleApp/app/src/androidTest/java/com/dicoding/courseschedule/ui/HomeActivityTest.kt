package com.dicoding.courseschedule.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//TODO 15 : Write UI test to validate when user tap Add Course (+) Menu, the AddCourseActivity is displayed
class HomeActivityTest {
    @get:Rule
    val rule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun addCourse(){
        onView(withId(R.id.action_add)).perform(click())
        onView(withText(R.string.add_course)).check(matches(isDisplayed()))
        onView(withId(R.id.add_course_name)).check(matches(isDisplayed()))
        onView(withId(R.id.add_course_lecturer)).check(matches(isDisplayed()))
        onView(withId(R.id.add_course_note)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
    }

}