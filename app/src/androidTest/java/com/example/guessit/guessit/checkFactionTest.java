package com.example.guessit.guessit;

import android.content.ComponentName;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getTargetContext;
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

/**
 * Created by AiLi on 2/17/2017.
 */

public class checkFactionTest {
    // Launch android activities
    @Rule
    public ActivityTestRule<MainActivity> mainact = new ActivityTestRule<>(MainActivity.class);

    @Test
    // Checks for basic case for user name
    public void selectFactionMonster() throws Exception {
        // Main activity launches first and selects join game
        onView(withId(R.id.startgame)).perform(click());
        // Types in John
        onView(withId(R.id.usernamefield)).perform(typeText("JohnD"));
        Espresso.closeSoftKeyboard();
        // Selects check user name
        onView(withId(R.id.checkusername2)).perform(click());
        // Selects Choose a faction
        onView(withId(R.id.gotofaction)).perform(click());
        // Selects Monster
        onView(withId(R.id.button4)).perform(click());
        // Some kind of check
        //intended(toPackage("com.example.guessit.guessit/.MonsterActivity"));
        onView(withId(R.id.imageButton)).check(matches(isDisplayed()));
    }

    @Test
    // Checks for basic case for user name
    public void selectFactionHuman() throws Exception {
        // Main activity launches first and selects join game
        onView(withId(R.id.startgame)).perform(click());
        // Types in John
        onView(withId(R.id.usernamefield)).perform(typeText("JohnD"));
        Espresso.closeSoftKeyboard();
        // Selects check user name
        onView(withId(R.id.checkusername2)).perform(click());
        // Selects Choose a faction
        onView(withId(R.id.gotofaction)).perform(click());
        // Selects Monster
        onView(withId(R.id.button5)).perform(click());
        // Some kind of check
        //intended(toPackage("com.example.guessit.guessit/.MonsterActivity"));
        onView(withId(R.id.imageButton5)).check(matches(isDisplayed()));
    }
}
