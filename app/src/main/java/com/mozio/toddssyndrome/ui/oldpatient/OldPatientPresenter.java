package com.mozio.toddssyndrome.ui.oldpatient;

import android.content.Context;
import android.view.View;

import com.mozio.toddssyndrome.data.database.sqlite.PatientInformationHelper;
import com.mozio.toddssyndrome.model.Patient;
import com.mozio.toddssyndrome.ui.Base.BasePresenter;

import rx.Observable;

/**
 * Created by mutha on 18/09/16.
 */
public class OldPatientPresenter extends BasePresenter {

    private Context context;
    private PatientInformationHelper patientInformationHelper;

    public OldPatientPresenter(View view) {
        context = view.getContext();
    }

    public void attach(View view){
        context = view.getContext();
    }

    public void detach(){
        context = null;
    }

    public Observable<Patient> getPatientInfo(String id){
        if(patientInformationHelper == null){
            patientInformationHelper = patientInformationHelper.getHelper(context.getApplicationContext());
        }
        // this runs on the background thread
        return patientInformationHelper.getPatientInfo(id);
    }
}
