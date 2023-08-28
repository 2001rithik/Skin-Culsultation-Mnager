package GUI;

import Encryption.Encryption;
import westminsterskinconsultation.Consultation;
import westminsterskinconsultation.Doctor;
import westminsterskinconsultation.Patient;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class ConsultationController {
    private LinkedList<Consultation> consultations = new LinkedList<>();
    private ArrayList<Doctor> rithikdoctors;
    public ConsultationController(ArrayList<Doctor> doctors){
        this.rithikdoctors = doctors;
    }

    public String addConsultation(Doctor doctor, Patient patient, Date consultationDate, LocalTime consultationTime, String additionalInfo, int noOfHours, ImageIcon imageOfDisease) throws IOException {

        if(isAvailable(consultationDate,consultationTime,noOfHours,doctor.getMedicalLicenceNumber())){
            Consultation tempConsultation = new Consultation(doctor,patient,consultationDate,consultationTime, noOfHours,calculateCost(patient.getPatientId(),noOfHours),additionalInfo,imageOfDisease);
            consultations.add(tempConsultation);
            return "successfully added record your consultation id is:" + tempConsultation.getConsultationId();
        }else{
            for (Doctor tempDoctor: rithikdoctors){
                if(isAvailable(consultationDate,consultationTime,noOfHours,tempDoctor.getMedicalLicenceNumber())){
                    Consultation tempConsultation = new Consultation(tempDoctor,patient,consultationDate,consultationTime, noOfHours,calculateCost(patient.getPatientId(),noOfHours),additionalInfo,imageOfDisease);
                    consultations.add(tempConsultation);
                    return "Requested doctor is unavailable consultation added to Dr." + tempDoctor.getName() + "\nconsultation id : " + tempConsultation.getConsultationId();
                }
            }
        }
        return "No doctors available for the requested time slot";
    }

    private double calculateCost(String patientId,int noOfHours){
        if(isFirstConsultation(patientId)){
            return noOfHours * 15;
        }else {
            return noOfHours * 25;
        }
    }

    //check if the Consultation is the first one of the patient
    private boolean isFirstConsultation(String patientId){
        for(Consultation c : consultations){
            if(c.getPatientID().equals(patientId)){
                return false;
            }
        }
        return true;
    }

    //check for the availability of a doctor
    public boolean isAvailable(Date consultationDate, LocalTime consultationTime, int noOfHours, String doctorLicenceNumber){

        for (Consultation consultation : consultations){
            if(consultation.getDoctorLicenceNumber().equals(doctorLicenceNumber)){
                boolean dateCheck = compareDates(consultationDate,consultation.getDate());

                LocalTime T1 = consultation.getTime(); //old consultation start time
                LocalTime T2 = consultation.getTime().plusHours(consultation.getNoOfHours()); //old consultation end time
                LocalTime t1 = consultationTime; //new consultation start time
                LocalTime t2 = consultationTime.plusHours(noOfHours); //new consultation end time

                boolean timeCheck1 = ( t1.isAfter(T1) ) && ( t1.isBefore(T2) );
                boolean timeCheck2 = ( t2.isAfter(T1) ) && ( t2.isBefore(T2) );
                boolean timeCheck3 = ( t1.isBefore(T1) && (t2.isAfter(T2)) );
                boolean timeCheck4 = (t1.equals(T1) || t1.equals(T2) || t2.equals(T1) || t2.equals(T2));

                if( (dateCheck) && ( (timeCheck1) || (timeCheck2) || (timeCheck3) || (timeCheck4) ) ){
                    return false;
                }
            }
        }
        return true;
    }

    public Consultation getConsultation(String consultationId){
        for (Consultation consultation : consultations){
            if(consultation.getConsultationId().equals(consultationId)){
                return consultation;
            }
        }
        return null;
    }

    //used this method to ignore time and compare dates
    public boolean compareDates(Date date1, Date date2){
        String dateStr1 = new SimpleDateFormat("yyyy-MM-dd").format(date1);
        String dateStr2 = new SimpleDateFormat("yyyy-MM-dd").format(date2);

        LocalDate ldate1 = LocalDate.parse(dateStr1);
        LocalDate ldate2 = LocalDate.parse(dateStr2);

        return ldate1.compareTo(ldate2) == 0;
    }
    public void saveData(){
        try  {
            FileOutputStream fos = new FileOutputStream("consultations.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Consultation consultation : consultations){
                oos.writeObject(consultation);
            }
            oos.close();
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
    }

    public void loadData(){
        try  {
            FileInputStream fos = new FileInputStream("consultations.txt");
            ObjectInputStream oos = new ObjectInputStream(fos);
            boolean eof = false;//to determine if reached end of the file
            int idCounter = 0; //to continue the consultation id generator
            while (!eof){
                try {
                    Consultation tempConsultation = (Consultation) oos.readObject();
                    consultations.add(tempConsultation);
                    idCounter++;
                }catch (EOFException e){
                    eof = true;
                }
            }
            Consultation.setIdCounter(idCounter);
            System.out.println("Successfully loaded data from file\n");
            oos.close();
        } catch (ClassNotFoundException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        }catch (IOException e){
            System.out.println("IOException " + e.getMessage());
        }
    }

}

