package westminsterskinconsultation;

import java.util.Date;

public class Patient extends Person {
    private String  patientId;

    public Patient(String patientId, String name, String surName, Date dateOfBirth, String mobileNumber) {
        super(name, surName, dateOfBirth, mobileNumber);
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }

}

