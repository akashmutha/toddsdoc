package com.mozio.toddssyndrome;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mozio.toddssyndrome.data.database.sqlite.PatientInformationHelper;
import com.mozio.toddssyndrome.model.Patient;
import com.mozio.toddssyndrome.ui.newpatient.NewPatientActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by mutha on 18/09/16.
 */

@RunWith(AndroidJUnit4.class)
public class SqliteDatabaseTest {

    @Rule
    public ActivityTestRule<NewPatientActivity> mActivityRule =
            new ActivityTestRule<>(NewPatientActivity.class);


    @Test
    public void ensureDBRecordEntryWork() {
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

        String id = onView(withId(R.id.tv_update_record_message)).toString();
        PatientInformationHelper patientInformationHelper = PatientInformationHelper
                .getHelper(mActivityRule.getActivity().getApplicationContext());

        patientInformationHelper.getPatientInfo(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Patient>() {
                    @Override
                    public void call(Patient patient) {
                        assert patient != null;
                    }
                });
    }
}
