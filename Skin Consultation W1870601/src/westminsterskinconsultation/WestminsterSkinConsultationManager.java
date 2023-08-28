package westminsterskinconsultation;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{

    private final ArrayList<Doctor> rithik = new ArrayList<>(10);


    @Override
    public void addDoctor(Doctor doctor) {

        if( (!checkForDuplicates(doctor)) && (rithik.size() < 10) ){
            rithik.add(doctor);
            System.out.println("Dr. " + doctor.getName() + " "+"added to the system.");
            return;
        }

        System.out.println("Dr. " + doctor.getName() + " Exited from the system.");
    }

    @Override
    public void deleteDoctor(String medicalLicenceNumber) {
        if(rithik.isEmpty()){
            System.out.println("Doctor list is already empty");
            return;
        }
        int index = searchDoctor(medicalLicenceNumber);//search the doctor want to remove and get his index no

        if(index > 0){
            Doctor temp = rithik.remove(index);
            System.out.println("\nDr. " + temp.getName()+"successfully removed from the system." +
                    "\nEnter medical licence number : " + temp.getMedicalLicenceNumber() +
                    "\n" + rithik.size() + " doctors remaining\n");
            return;
        }
        System.out.println("\nInvalid licence number or No results found.\n");
    }

    @Override
    public void displayListOfDoctors() {
        Collections.sort(rithik);
        ListIterator<Doctor> iterator = rithik.listIterator();

        if(rithik.isEmpty()){
            System.out.println("No Doctors found in the system.");
        }else{
            while(iterator.hasNext()){
                System.out.println(iterator.next().toString() + "\n" + "*".repeat(50));
            }
        }


    }

    @Override
    public void saveInFile() {
        try  {
            FileOutputStream fos = new FileOutputStream("doctors.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Doctor doctor : rithik){
                oos.writeObject(doctor);
            }
            oos.close();
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
        System.out.println("System data stored successfully\n");
    }

    @Override
    public void loadFromFile() {
        try  {
            FileInputStream fos = new FileInputStream("doctors.txt");
            ObjectInputStream oos = new ObjectInputStream(fos);
            boolean eof = false;//to determine if rea
            while (!eof){
                try {
                    Doctor tempDoctor = (Doctor) oos.readObject();
                    rithik.add(tempDoctor);
                }catch (EOFException e){
                    eof = true;
                }
            }
            System.out.println("File data loaded successfully\n");
            oos.close();
        } catch (ClassNotFoundException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        }catch (IOException e){
            System.out.println("IOException " + e.getMessage());
        }
    }

    //sort doctors


    //returns true if there are doctor in the list with same licence number or mobile number
    private boolean checkForDuplicates(Doctor doctor){

        for(Doctor d : rithik){
            if( (d.getMedicalLicenceNumber().equals(doctor.getMedicalLicenceNumber())) ||
                    (d.getMobileNumber().equals(doctor.getMobileNumber())) ){
                return true;
            }
        }

        return false;
    }

    //returns the index of the found doctor or return -1 if failed to found
    public int searchDoctor(String medicalLicenceNumber){
        for(Doctor d : rithik){
            if(d.getMedicalLicenceNumber().equals(medicalLicenceNumber)){
                return rithik.indexOf(d);
            }
        }

        return -1;
    }

    //method to create a table model to draw a table
    public DefaultTableModel getDoctorList(){

        Object [][] tableData = new Object[rithik.size()][4];

        for(int i = 0; i < rithik.size(); i++){
            tableData [i][0] = rithik.get(i).getName() + " " + rithik.get(i).getSurName();
            tableData [i][1] = rithik.get(i).getSpecialisation();
            tableData [i][2] = rithik.get(i).getMobileNumber();

            tableData [i][3] = rithik.get(i).getMedicalLicenceNumber();

        }
        Object [] columns = new Object[]{"Doctors Name", "Specialisation", "Mobile Number","Licence Number"};
        //add columns and table data to a table model and return it
        return new DefaultTableModel(tableData,columns);
    }
    //method to return doctors to display in comboBox/dropDown
    public String [] getComboList(){
        String [] comboList = new String[rithik.size()];
        for(int i = 0; i < rithik.size(); i++){
            comboList[i] = rithik.get(i).getName() + "-" + rithik.get(i).getMedicalLicenceNumber();
        }
        return comboList;
    }

    //method to return a doctor at specified index
    public Doctor getDoctorByIndex(int index){
        return this.rithik.get(index);
    }

    //method to return full list of doctors
    public ArrayList<Doctor> getDoctors() {
        return rithik;
    }
}

