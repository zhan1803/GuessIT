package com.example.guessit.guessit;

/**
 * Created by zhan1803 on 2/16/2017.
 */

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class scoreTableTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void scoreButton_GamePage() {
        onView(withId(R.id.startgame)).perform(click());
        onView(withId(R.id.usernamefield)).perform(typeText("jjj"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.checkusername2)).perform(click());
        onView(withId(R.id.gotofaction)).perform(click());
        onView(withId(R.id.button4)).perform(click());
        //onView(withId(R.id.button_toHintGiver)).perform(click());
        onView(withId(R.id.viewScoreButton)).perform(click());
        onView(withId(R.id.viewScoreButton)).check(matches(isEnabled()));
    }

    @Test
    public void scoreButton_ResultPage() {
        onView(withId(R.id.startgame)).perform(click());
        onView(withId(R.id.usernamefield)).perform(typeText("jjj"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.checkusername2)).perform(click());
        onView(withId(R.id.gotofaction)).perform(click());
        onView(withId(R.id.button4)).perform(click());
        //onView(withId(R.id.button_toHintGiver)).perform(click());
        //onView(withId(R.id.testResult)).perform(click());
        // Press the ready button
        onView(withId(R.id.scoreResultButton)).perform(click());
        // Check if a dialog is popped up
        onView(withId(R.id.scoreResultButton)).check(matches(isEnabled()));
    }

}