package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    private void addCity(String name) {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());
    }

    /**
     * Test1: Check the activity correctly switched
     * Adding Edmonton -> click position 0 -> check ShowActivity's Back button show up.
     */
    @Test
    public void testSwitchToShowActivity() {
        addCity("Edmonton");
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());
        onView(withId(R.id.button_back)).check(matches(isDisplayed()));
    }

    /**
     * Test2: Test if the city name is consistent
     * BY: Adding Edmonton -> click -> check ShowActivity's TextView show Edmonton
     */
    @Test
    public void testCityNameConsistent(){
        addCity("Edmonton");
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());
        onView(withId(R.id.text_city_name)).check(matches(withText("Edmonton")));
    }


    /**
     * Test3: Test the back button
     * BY: ShowActivity -> click BACK -> check back to MainActivity
     */
    @Test
    public void testBackButton(){
        addCity("Edmonton");
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());
        onView(withId(R.id.button_back)).perform(click());
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }
}
