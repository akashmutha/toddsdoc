package com.mozio.toddssyndrome.ui.newpatient;

import android.content.Context;
import android.view.View;

import com.mozio.toddssyndrome.data.database.sqlite.PatientInformationHelper;
import com.mozio.toddssyndrome.model.Patient;
import com.mozio.toddssyndrome.ui.Base.BasePresenter;

import rx.Observable;

/**
 * Created by mutha on 18/09/16.
 */
public class NewPatientPrensenter extends BasePresenter{

    private Context context;
    private PatientInformationHelper patientInformationHelper;

    public NewPatientPrensenter(View view) {
        context = view.getContext();
    }

    public void attach(View view){
        context = view.getContext();
    }

    public void detach(){
        context = null;
    }

    public Observable<String> insertPatientInfo(Patient patient){
        patient.setPatientId(Long.toString(System.currentTimeMillis()));
        if(patientInformationHelper == null){
            patientInformationHelper = patientInformationHelper.getHelper(context.getApplicationContext());
        }
        return patientInformationHelper.insertPatientInfo(patient);
    }
}
