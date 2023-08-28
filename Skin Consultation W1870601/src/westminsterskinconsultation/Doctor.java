package westminsterskinconsultation;

import java.io.Serializable;
import java.util.Date;


public class Doctor extends Person implements Comparable<Doctor> , Serializable {
    private final String medicalLicenceNumber;
    private final String specialisation;


    public Doctor(String medicalLicenceNumber, String specialisation, String name, String surName, Date dateOfBirth, String mobileNumber) {
        super(name, surName, dateOfBirth, mobileNumber);
        this.medicalLicenceNumber = medicalLicenceNumber;
        this.specialisation = specialisation;
    }

    public String getMedicalLicenceNumber() {
        return medicalLicenceNumber;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    @Override
    public int compareTo(Doctor doctor) {

        if(doctor.getSurName().equalsIgnoreCase(this.getSurName())){
            return 0;
        }else if(doctor.getSurName().compareToIgnoreCase(this.getSurName()) < 0){
            return 1;
        }else {
            return -1;
        }

    }

    @Override
    public String toString() {
        return super.toString() +
                "\nMedical Licence Number = " + medicalLicenceNumber +
                "\nSpecialisation = " + specialisation;
    }

}
