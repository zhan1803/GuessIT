package com.example.guessit.guessit;

import android.app.Activity;
import android.graphics.Paint;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;
import android.os.IBinder;
import android.support.test.espresso.Root;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.PendingIntent.getActivity;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;

/**
 * Created by AiLi on 2/17/2017.
 */
@RunWith(AndroidJUnit4.class)
public class usernameTest{
        // Launch android activities
        @Rule
        public ActivityTestRule<MainActivity> mainact = new ActivityTestRule<>(MainActivity.class);

        @Test
        // Checks for basic case for user name
        public void insertUserName() throws Exception {
            // Main activity launches first and selects join game
            onView(withId(R.id.joingame)).perform(click());
            // Types in John
            onView(withId(R.id.username)).perform(typeText("John"));
            // Selects check user name
            onView(withId(R.id.checkusername)).perform(click());
                    onView(withText(startsWith("Awesome"))).
                    inRoot(withDecorView(not(is(mainact.getActivity().
                            getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

    @Test
    // Checks that it is a valid user name
    public void checkUserNameValid1() throws Exception {
        // Main activity -> selects join game
        onView(withId(R.id.joingame)).perform(click());
        // Types in @@@John
        onView(withId(R.id.username)).perform(typeText("@@@John"));
        // Selects check user name
    onView(withId(R.id.checkusername)).perform(click());
    onView(withText("Username must only be alphanumeric with no space")).
    inRoot(withDecorView(not(is(mainact.getActivity().
    getWindow().getDecorView())))).check(matches(isDisplayed()));
}

    @Test
    // Checks that it is a valid user name
    public void checkUserNameValid2() throws Exception {
        // Main activity -> selects join game
        onView(withId(R.id.joingame)).perform(click());
        // Types in John Doe
        onView(withId(R.id.username)).perform(typeText("John Doe"));
        // Selects check user name
        onView(withId(R.id.checkusername)).perform(click());
        onView(withText("Username must only be alphanumeric with no space")).
                inRoot(withDecorView(not(is(mainact.getActivity().
                        getWindow().getDecorView())))).check(matches(isDisplayed()));
    }


}
