package com.mozio.toddssyndrome.ui.newpatient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mozio.toddssyndrome.R;
import com.mozio.toddssyndrome.model.Patient;
import com.mozio.toddssyndrome.ui.oldpatient.OldPatientActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mutha on 18/09/16.
 */
public class NewPatientFragment extends Fragment implements View.OnClickListener {

    private ScrollView parentLayout;
    private TextView name, age, updateMessage, updateRecordMessage;
    private RadioGroup gender, migraines, hallucinogenicDrugs;
    private Button checkSyndrome, clearInfo, oldPatientInfo;
    private NewPatientPrensenter newPatientPrensenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retain the fragment during device rotation
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        parentLayout = (ScrollView)inflater.inflate(R.layout.new_patient_info_fragment, container, false);
        name = (TextView)parentLayout.findViewById(R.id.tv_name);
        age = (TextView)parentLayout.findViewById(R.id.tv_age);
        gender = (RadioGroup)parentLayout.findViewById(R.id.rg_gender);
        migraines = (RadioGroup)parentLayout.findViewById(R.id.rg_migraines);
        hallucinogenicDrugs = (RadioGroup)parentLayout.findViewById(R.id.rg_hdrugs);
        checkSyndrome = (Button)parentLayout.findViewById(R.id.button_check_todd);
        clearInfo = (Button)parentLayout.findViewById(R.id.button_clear_info);
        oldPatientInfo = (Button)parentLayout.findViewById(R.id.button_old_patient_info);
        updateMessage = (TextView)parentLayout.findViewById(R.id.tv_update_message);
        updateRecordMessage = (TextView)parentLayout.findViewById(R.id.tv_update_record_message);
        checkSyndrome.setOnClickListener(this);
        clearInfo.setOnClickListener(this);
        oldPatientInfo.setOnClickListener(this);
        return parentLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(newPatientPrensenter == null){
            newPatientPrensenter = new NewPatientPrensenter(parentLayout);
        } else {
            newPatientPrensenter.attach(parentLayout);
        }
        if(savedInstanceState != null){
            if(savedInstanceState.getString("update_message") != null){
                updateMessage.setVisibility(View.VISIBLE);
                updateMessage.setText(savedInstanceState.getString("update_message"));
            }
            if(savedInstanceState.getString("update_record_message") != null){
                updateRecordMessage.setVisibility(View.VISIBLE);
                updateRecordMessage.setText(savedInstanceState.getString("update_record_message"));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(updateMessage != null && updateMessage.getText() != null && !updateMessage.getText().toString().equals("")) {
            outState.putString("update_message", updateMessage.getText().toString());
        } else {
            outState.putString("update_message", null);
        }
        if(updateRecordMessage != null && updateRecordMessage.getText() != null && !updateRecordMessage.getText().toString().equals("")){
            outState.putString("update_record_message", updateRecordMessage.getText().toString());
        } else {
            outState.putString("update_record_message", null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // detach the presenter so activity leak won't happen
        newPatientPrensenter.detach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_check_todd:
                if(validateInput()){
                    final Patient patient = getPatientDetails();
                    // these funcitons run on background thread and response runs on main thread
                    newPatientPrensenter.checkIfHaveToddSndrome(patient)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    // this is on main thread as observe on is set as main thread
                                    updateMessage.setVisibility(View.VISIBLE);
                                    updateMessage.setText(s);
                                }
                            });

                    newPatientPrensenter.insertPatientInfo(patient)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    // this is on main thread as observe on is set as main thread
                                    updateRecordMessage.setVisibility(View.VISIBLE);
                                    updateRecordMessage.setText(patient.getName() + " has been added"
                                            + " to the records. Unique id of the patient is "
                                            + patient.getPatientId());
                                }
                            });
                } else {
                    updateMessage.setVisibility(View.VISIBLE);
                    updateMessage.setText("Please fill all the details !");
                }
                break;

            case R.id.button_clear_info:
                clearAllFields();
                break;

            case R.id.button_old_patient_info:
                Intent intent = new Intent(getActivity().getApplicationContext(), OldPatientActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            default:
                break;
        }
    }

    private boolean validateInput(){
        if(name.getText() == null || "".equals(name.getText().toString()) || age.getText() == null
                || "".equals(age.getText().toString()) || gender.getCheckedRadioButtonId() == -1
                || migraines.getCheckedRadioButtonId() == -1
                || hallucinogenicDrugs.getCheckedRadioButtonId() == -1){
            return false;
        }
        return true;
    }

    private void clearAllFields(){
        name.setText("");
        age.setText("");
        gender.clearCheck();
        migraines.clearCheck();
        hallucinogenicDrugs.clearCheck();
        updateMessage.setText("");
        updateRecordMessage.setText("");
        updateMessage.setVisibility(View.GONE);
        updateRecordMessage.setVisibility(View.GONE);
    }

    private Patient getPatientDetails(){
        Patient patient = new Patient();
        patient.setName(name.getText().toString());
        patient.setAge(Integer.valueOf(age.getText().toString()));
        if(gender.getCheckedRadioButtonId() == R.id.rg_gender_male){
            patient.setSex("male");
        } else {
            patient.setSex("female");
        }

        if(migraines.getCheckedRadioButtonId() == R.id.rg_migraines_yes){
            patient.setHasMigraines(true);
        } else{
            patient.setHasMigraines(false);
        }

        if(hallucinogenicDrugs.getCheckedRadioButtonId() == R.id.rg_hdrugs_yes){
            patient.setHasUsedHallucinogenicDrugs(true);
        } else{
            patient.setHasUsedHallucinogenicDrugs(false);
        }
        return patient;
    }
}
