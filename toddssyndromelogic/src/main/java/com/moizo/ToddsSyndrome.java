package com.moizo;

/**
 * This class helps to find out how probable a man/woman is
 * having Todd's Syndrome
 * @author mutha
 *
 */

public class ToddsSyndrome {

    private boolean hasMigraines;
    private boolean hasUsedHallucinogenicDrugs;
    private int noOfTotalAttributes;
    private int age;
    private String sex;

    public ToddsSyndrome(final String sex, final boolean hasMigraines,
                         final int age, final boolean hasUsedHallucinogenicDrugs){
        this.sex = sex;
        this.hasMigraines = hasMigraines;
        this.age = age;
        this.hasUsedHallucinogenicDrugs = hasUsedHallucinogenicDrugs;
        this.noOfTotalAttributes = 4;
    }

    public float getProbabilityOfToddsSyndrome(){
        float probabilityWeightage = 0;

        if(this.age <= 15) probabilityWeightage++; // each attribute has an equal weightage

        if("male".equals(this.sex)) probabilityWeightage++;

        if(this.hasMigraines) probabilityWeightage++;

        if(this.hasUsedHallucinogenicDrugs) probabilityWeightage++;

        return (probabilityWeightage / noOfTotalAttributes);
    }
}
