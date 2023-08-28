package westminsterskinconsultation;

import Encryption.Encryption;

import javax.swing.*;
import java.io.*;
import java.time.LocalTime;
import java.util.Base64;
import java.util.Date;

public class Consultation implements Serializable{
    private String consultationId;
    private Doctor doctor;
    private Patient patient;
    private Date date;
    private LocalTime time;
    private int noOfHours;
    private double cost;
    private String notes;
    private String encryptedImage;
    private Encryption encryption;
    private static int idCounter = 0;
    private static String decryptionKey = "327462340987234";
    public Consultation(Doctor doctor, Patient patient, Date date, LocalTime time,int noOfHours, double cost, String notes, ImageIcon diseasedAreaImage) throws IOException {

        this.encryption = new Encryption();//used to encrypt due to privacy
        this.encryptedImage = encryption.encrypt(toString(diseasedAreaImage),decryptionKey);//encode image to string and then encrypt
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;
        this.noOfHours = noOfHours;
        this.cost = cost;
        this.notes = encryption.encrypt(notes,decryptionKey);//encrypt the notes and store it
        this.consultationId = createId();
    }

    //Encode image to string
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( bAOS );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(bAOS.toByteArray());
    }

    //Decode image from string
    private static ImageIcon fromString(String s ) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        ImageIcon o  = (ImageIcon) ois.readObject();
        ois.close();
        return o;
    }
    public ImageIcon getDecryptedImage() throws IOException, ClassNotFoundException {
        return fromString(encryption.decrypt(encryptedImage,decryptionKey)); //decrypt image and return it
    }
    public String getPatientID(){
        return patient.getPatientId();
    }
    public String getDoctorLicenceNumber(){
        return doctor.getMedicalLicenceNumber();
    }
    public Date getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getNoOfHours() {
        return noOfHours;
    }

    private String createId(){
        return "CL10" + idCounter++;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public String [] toStringArray() {
        return new String[] {"Consultation ID ='" + consultationId,
                "Doctor Name =" + doctor.getName(),
                "Patient Name =" + patient.getName(),
                "Date =" + getDateString(),
                "Time =" + time.getHour() + "h" + time.getMinute() + "min",
                "No Of Hours =" + noOfHours,
                "Cost =" + "£" + cost + '\n',
                "Notes =" + encryption.decrypt(notes,decryptionKey)};
    }

    public String getDateString(){
        return (date.getYear() + 1900) + "." + (date.getMonth() + 1) + "." + date.getDate();
    }

    public static void setIdCounter(int idCounter){
        Consultation.idCounter = idCounter;
    }

}
