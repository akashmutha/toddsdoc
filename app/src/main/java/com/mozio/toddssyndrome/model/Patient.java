package com.mozio.toddssyndrome.model;

/**
 * Created by mutha on 18/09/16.
 */
public class Patient {

    private boolean hasMigraines;
    private boolean hasUsedHallucinogenicDrugs;
    private String patientId;
    private String name;
    private String sex;
    private int age;

    public boolean hasUsedHallucinogenicDrugs() {
        return this.hasUsedHallucinogenicDrugs;
    }

    public void setHasUsedHallucinogenicDrugs(boolean hasUsedHallucinogenicDrugs) {
        this.hasUsedHallucinogenicDrugs = hasUsedHallucinogenicDrugs;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatientId() {
        return this.patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public boolean hasMigraines() {
        return this.hasMigraines;
    }

    public void setHasMigraines(boolean hasMigraines) {
        this.hasMigraines = hasMigraines;
    }
}
