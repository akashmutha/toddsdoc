package com.mozio.toddssyndrome;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mozio.toddssyndrome.ui.newpatient.NewPatientActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by mutha on 18/09/16.
 */

@RunWith(AndroidJUnit4.class)
public class NewPatientActivityTest {

    @Rule
    public ActivityTestRule<NewPatientActivity> mActivityRule =
            new ActivityTestRule<>(NewPatientActivity.class);

    @Test
    public void ensureInputValidationWork() {
        // type in only one field and then press check there should be a text saying please fill
        // all the field. It's general and can be checked for each field's one by one.
        onView(withId(R.id.tv_name))
                .perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(R.id.button_check_todd)).perform(click());
        onView(withId(R.id.tv_update_message)).check(matches(withText("Please fill all the details !")));
    }

    @Test
    public void ensureClearInfoWork() {
        // type one dummy patient's info and after clicking check whether all are reset or not
        onView(withId(R.id.tv_name))
                .perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(R.id.tv_age))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.rg_gender_male)).perform(click());
        onView(withId(R.id.rg_migraines_no)).perform(click());
        onView(withId(R.id.rg_hdrugs_yes)).perform(click());
        onView(withId(R.id.button_clear_info)).perform(click());
        onView(withId(R.id.tv_name)).check(matches(withText("")));
        onView(withId(R.id.tv_age)).check(matches(withText("")));
        onView(withId(R.id.rg_gender_male)).check(matches(not(isChecked())));
        onView(withId(R.id.rg_migraines_no)).check(matches(not(isChecked())));
        onView(withId(R.id.rg_hdrugs_yes)).check(matches(not(isChecked())));
    }

    @Test
    public void ensureLogicWork() {
        // type one dummy case and check if it works! Can be written for all general scenarios.
        onView(withId(R.id.tv_name))
                .perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(R.id.tv_age))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.rg_gender_male)).perform(click());
        onView(withId(R.id.rg_migraines_no)).perform(click());
        onView(withId(R.id.rg_hdrugs_yes)).perform(click());
        onView(withId(R.id.button_check_todd)).perform(click());
        onView(withId(R.id.tv_update_message)).check(matches(withText("HELLO has 75.0% likely " +
                "chance to have Todd's Syndrome !")));
    }
}
