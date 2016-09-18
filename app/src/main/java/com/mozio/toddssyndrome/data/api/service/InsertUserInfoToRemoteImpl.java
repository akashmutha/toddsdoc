package com.mozio.toddssyndrome.data.api.service;

import android.util.Log;

import com.mozio.toddssyndrome.data.api.server.RemoteServerAPI;
import com.mozio.toddssyndrome.model.Patient;
import com.mozio.toddssyndrome.model.SuccessBaseResponse;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mutha on 18/09/16.
 */

//Part of the api code, not used as I don't have a real domain to send data
public class InsertUserInfoToRemoteImpl implements InsertUserInfoToRemote {

    private RemoteServerAPI remoteServerAPI;

    @Inject
    public InsertUserInfoToRemoteImpl(RemoteServerAPI remoteServerAPI) {
        this.remoteServerAPI = remoteServerAPI;
    }

    @Override
    public Observable<SuccessBaseResponse> saveUserInfoOnRemote(final Patient patient) {
        return Observable.just(patient)
                .flatMap(new Func1<Patient, Observable<? extends SuccessBaseResponse>>() {
                    @Override
                    public Observable<? extends SuccessBaseResponse> call(Patient patient1) {
                        Log.e("request thread", Thread.currentThread().getName());
                        return remoteServerAPI.insertUserInfo(patient.getName(), patient.getPatientId(),
                                patient.getSex(), patient.getAge(), patient.hasMigraines(),
                                patient.hasUsedHallucinogenicDrugs());
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread());
    }
}
