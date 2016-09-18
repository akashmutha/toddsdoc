package com.mozio.toddssyndrome.ui.oldpatient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mozio.toddssyndrome.R;
import com.mozio.toddssyndrome.model.Patient;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by mutha on 18/09/16.
 */
public class OldPatientFragment extends Fragment implements View.OnClickListener {

    private ScrollView parentLayout;
    private TextView name, age, updateMessage, updateRecordMessage;
    private TextView patientId, gender, hallucinogenicDrugs, migraines;
    private Button getInfo, newPatient;
    private OldPatientPresenter oldPatientPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        parentLayout = (ScrollView)inflater.inflate(R.layout.old_patient_info_fragment, container, false);
        patientId = (EditText)parentLayout.findViewById(R.id.tv_patient_id);
        name = (TextView)parentLayout.findViewById(R.id.tv_name);
        age = (TextView)parentLayout.findViewById(R.id.tv_age);
        gender = (TextView)parentLayout.findViewById(R.id.tv_gender);
        migraines = (TextView)parentLayout.findViewById(R.id.tv_migraine);
        hallucinogenicDrugs = (TextView)parentLayout.findViewById(R.id.tv_h_drugs);
        getInfo = (Button)parentLayout.findViewById(R.id.button_get_info);
        newPatient = (Button)parentLayout.findViewById(R.id.button_new_patient);
        updateMessage = (TextView)parentLayout.findViewById(R.id.tv_update_message);
        updateRecordMessage = (TextView)parentLayout.findViewById(R.id.tv_update_record_message);
        getInfo.setOnClickListener(this);
        newPatient.setOnClickListener(this);
        return parentLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(oldPatientPresenter == null){
            oldPatientPresenter = new OldPatientPresenter(parentLayout);
        } else {
            oldPatientPresenter.attach(parentLayout);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        oldPatientPresenter.detach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_get_info:
                patientId.clearFocus();
                if(patientId.getText() != null && patientId.getText() != ""){
                    oldPatientPresenter.getPatientInfo(patientId.getText().toString())
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Patient>() {
                                @Override
                                public void call(Patient patient) {
                                    // this executes on main thread only
                                    if(patient != null) {
                                        updateView(patient);
                                    } else {
                                        updateMessage.setVisibility(View.VISIBLE);
                                        updateMessage.setText("This id is not valid, please check!");
                                    }
                                }
                            });
                }
                break;

            case R.id.button_new_patient:
                getActivity().onBackPressed();
                break;

            default:
                break;
        }
    }

    private void updateView(Patient patient){
        updateMessage.setVisibility(View.GONE);
        name.setText("Name : " + patient.getName());
        age.setText("Age : " + patient.getAge());
        gender.setText("Gender : " + patient.getSex());
        if(patient.hasMigraines()) {
            migraines.setText("Patient had migraines : " + "Yes");
        } else {
            migraines.setText("Patient had migraines : " + "No");
        }

        if(patient.hasUsedHallucinogenicDrugs()){
            hallucinogenicDrugs.setText("Patient used any hallucinogenic drugs: " + "Yes ");
        } else {
            hallucinogenicDrugs.setText("Patient used any hallucinogenic drugs: " + "No ");
        }

        oldPatientPresenter.checkIfHaveToddSndrome(patient)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        // this runs on main thread as observe on is set as main thread
                        updateMessage.setVisibility(View.VISIBLE);
                        updateMessage.setText(s);
                    }
                });
    }
}
