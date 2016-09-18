package com.mozio.toddssyndrome.data.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.mozio.toddssyndrome.model.Patient;
import com.mozio.toddssyndrome.util.Constants;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mutha on 18/09/16.
 */
public class PatientInformationHelper extends SQLiteOpenHelper {

    private static PatientInformationHelper instanceOfPatientInformationHelper;
    private static final int DATABASE_VERSION = 1;
    // sqlite doesn't have inbuit boolean ( it converts it to int)
    private static final String CREATE_TABLE_PATIENT = "create table " + Constants.PATIENT_TABLE + " (" +
            "patient_id" + " TEXT PRIMARY KEY," +
            "name" + " TEXT," +
            "sex" + " TEXT, " +
            "age" + " INT, " +
            "has_migraines" + " INT," +
            "has_used_hallucinogenic_Drug" + " INT)";


    public static synchronized PatientInformationHelper getHelper(Context context){
        if(instanceOfPatientInformationHelper == null){
            instanceOfPatientInformationHelper = new PatientInformationHelper(context);
        }
        return  instanceOfPatientInformationHelper;
    }

    public PatientInformationHelper(Context context) {
        super(context, Constants.PATIENT_DB, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_PATIENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Observable<String> insertPatientInfo(final Patient patient) throws SQLiteException {
        return Observable.just(patient).flatMap(new Func1<Patient, Observable<? extends String>>() {
            @Override
            public Observable<? extends String> call(Patient newPatient) {
                SQLiteDatabase db = getWritableDatabase();
                Patient patientInfo = getInfo(patient.getPatientId());
                ContentValues contentValues = getContentValues(newPatient);

                //If the patient id doesn't exist, insert it into the database
                if (patientInfo == null) {
                    db.insert(Constants.PATIENT_TABLE, null, contentValues);
                } else {
                    return Observable.just(null);
                }
                return Observable.just(patient.getPatientId());
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
    }

    @NonNull
    private ContentValues getContentValues(Patient patient) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("patient_id", patient.getPatientId());
        contentValues.put("name", patient.getName());
        contentValues.put("age", patient.getAge());
        contentValues.put("sex", patient.getSex());
        if(patient.hasMigraines()) {
            contentValues.put("has_migraines", 1);
        } else {
            contentValues.put("has_migraines", 0);
        }

        if(patient.hasUsedHallucinogenicDrugs()) {
            contentValues.put("has_used_hallucinogenic_Drug", 1);
        } else {
            contentValues.put("has_used_hallucinogenic_Drug", 0);
        }
        return contentValues;
    }

    private Patient getInfo(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Constants.PATIENT_TABLE
                + " where " + "patient_id" + " = ?", new String[]{id});
        if (cursor != null && cursor.moveToFirst()) {
            Patient patient = getPatientInfoFromCursor(cursor, id);
            closeCursor(cursor);
            return patient;
        }
        closeCursor(cursor);
        return null;
    }

    @NonNull
    private void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    public Observable<Patient> getPatientInfo(String id) throws SQLiteException {
        return Observable.just(id).flatMap(new Func1<String, Observable<Patient>>() {
            @Override
            public Observable<Patient> call(String id) {
                Patient patient = getInfo(id);
                if (patient != null) {
                    return Observable.just(patient);
                }
                return Observable.just(null);
            }
        }).subscribeOn(Schedulers.io());
    }

    @NonNull
    private Patient getPatientInfoFromCursor(Cursor cursor, String id) {
        Patient patient = new Patient();
        patient.setPatientId(cursor.getString(cursor.getColumnIndex("patient_id")));
        patient.setSex(cursor.getString(cursor.getColumnIndex("sex")));
        patient.setAge(cursor.getInt(cursor.getColumnIndex("age")));

        if(cursor.getInt(cursor.getColumnIndex("has_migraines")) == 0){
            patient.setHasMigraines(false);
        } else {
            patient.setHasMigraines(true);
        }

        if(cursor.getInt(cursor.getColumnIndex("has_used_hallucinogenic_Drug")) == 0){
            patient.setHasUsedHallucinogenicDrugs(false);
        } else {
            patient.setHasUsedHallucinogenicDrugs(true);
        }

        patient.setName(cursor.getString(cursor.getColumnIndex("name")));
        return patient;
    }
}
