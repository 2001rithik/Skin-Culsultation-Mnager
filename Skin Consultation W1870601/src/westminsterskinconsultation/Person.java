package westminsterskinconsultation;

import java.io.Serializable;
import java.util.Date;


//implemented serializable interface to store doctor object to .dat file
abstract class Person implements Serializable{
    private final String name;
    private final String surName;
    private final Date dateOfBirth;
    private final String mobileNumber;

    public Person(String name, String surName, Date dateOfBirth, String mobileNumber) {
        this.name = name;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    @Override
    public String toString() {
        return  "Name = " + name + " " + surName +
                "\nDate Of Birth = " + getDate() +
                "\nMobile Number = " + mobileNumber;
    }

    public String getDate(){
        return dateOfBirth.getYear() + "." + dateOfBirth.getMonth() + "." + dateOfBirth.getDate();
    }

}
