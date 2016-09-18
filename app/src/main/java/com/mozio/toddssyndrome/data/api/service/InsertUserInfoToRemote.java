package com.mozio.toddssyndrome.data.api.service;

import com.mozio.toddssyndrome.model.Patient;
import com.mozio.toddssyndrome.model.SuccessBaseResponse;

import rx.Observable;

/**
 * Created by mutha on 18/09/16.
 */
public interface InsertUserInfoToRemote {
    Observable<SuccessBaseResponse> saveUserInfoOnRemote(Patient patient);
}
