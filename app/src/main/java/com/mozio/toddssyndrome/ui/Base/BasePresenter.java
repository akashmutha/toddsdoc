package com.mozio.toddssyndrome.ui.Base;

import com.moizo.ToddsSyndrome;
import com.mozio.toddssyndrome.model.Patient;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by mutha on 18/09/16.
 */
public abstract class BasePresenter {

    public Observable<String> checkIfHaveToddSndrome(Patient patient){
        return Observable.just(patient).flatMap(new Func1<Patient, Observable<? extends String>>() {
            @Override
            public Observable<? extends String> call(Patient patient) {
                ToddsSyndrome toddsSyndrome = new ToddsSyndrome(patient.getSex(),
                        patient.hasMigraines(), patient.getAge(), patient.hasUsedHallucinogenicDrugs());
                float probabilityOfToddsSyndrome = toddsSyndrome.getProbabilityOfToddsSyndrome();

                return Observable.just(patient.getName() + " has " + (probabilityOfToddsSyndrome *
                        100) + "% likely " + "chance to have Todd's Syndrome !");
            }
        });
    }

}
