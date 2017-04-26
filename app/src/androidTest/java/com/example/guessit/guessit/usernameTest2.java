package com.example.guessit.guessit;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by AiLi on 2/17/2017.
 */

public class usernameTest2 {
    @Rule
    public ActivityTestRule<MainActivity> mainact = new ActivityTestRule<>(MainActivity.class);



    @Test
    // Checks that it is a valid user name
    public void checkUserNameLengthTooLong() throws Exception {
        // Main activity -> selects join game
        onView(withId(R.id.joingame)).perform(click());
        // Types in qwertyuiopasdfghjklzxcvbnm
        onView(withId(R.id.username)).perform(typeText("qwertyuiopasdfghjklzxcvbnm"));
        // Selects check user name
        onView(withId(R.id.checkusername)).perform(click());
        onView(withText("Username too long")).
                inRoot(withDecorView(not(is(mainact.getActivity().
                        getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    // Checks that it is a valid user name
    public void checkUserNameLengthTooShort() throws Exception {
        // Main activity -> selects join game
        onView(withId(R.id.joingame)).perform(click());
        // Types in nothing
        onView(withId(R.id.username)).perform(typeText(""));
        // Selects check user name
        onView(withId(R.id.checkusername)).perform(click());
        // Instead of using toast to check, checked if submit button is enabled, which it isnt.
        onView(withId(R.id.submitcode)).check(matches(not(isEnabled())));
        //onView(withText("Username too short")).
        //        inRoot(withDecorView(not(is(mainact.getActivity().
        //                getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

}
