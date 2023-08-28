package GUI;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import westminsterskinconsultation.Doctor;
import westminsterskinconsultation.Patient;
import westminsterskinconsultation.WestminsterSkinConsultationManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class MainFrame extends JFrame implements ActionListener {

    private WestminsterSkinConsultationManager manager;

    private ConsultationController consultationController;
    private JTable doctorTable;
    private String [][] tableData;

    //Text fields
    private MyTextField fullName;
    private JFormattedTextField mobileNumber;
    private MyTextField patientId;
    private TextArea additionalInfo;
    //Dates
    private JDatePickerImpl patientBirthDay;
    private JDatePickerImpl consultationDate;
    private JFormattedTextField timeField;
    private JFormattedTextField noOfHours;








    //Buttons
    private MyButton browseImage = new MyButton("Choose Image",new Color(189, 36, 194));

    private MyButton addConsultationB = new MyButton("ADD consultation", new Color(231, 22, 22));
    private MyButton sortDoctors = new MyButton("<html>&nbsp;&nbsp;&nbsp;Sort<br>Doctors</html>",new Color(222, 205, 12));
    private MyButton checkAvailability = new MyButton("Check availability",new Color(215, 23, 23));
    private MyButton reviewConsultation = new MyButton("<html>&nbsp;Check your<br>consultation</html>",new Color(222, 205, 12));
    private MyButton saveData = new MyButton("<html>&nbsp;&nbsp;&nbsp;Save<br>Data</html>",new Color(222, 205, 12));
    //Drop Downs
    private JComboBox selectionList;
    //Image
    private static boolean isDataLoaded = true;//to restrict loading data into file multiple times
    private ImageIcon imageOfDisease;
    public MainFrame(WestminsterSkinConsultationManager manager) {
        this.manager = manager;
        this.consultationController = new ConsultationController(manager.getDoctors());

        //load consultation data into the program
        if(isDataLoaded){
            consultationController.loadData();
            isDataLoaded = false;
        }

        this.setSize(1000,700);
        this.setLayout(new BorderLayout());

        //Create a table and add it to scroll pane
        doctorTable = new JTable(manager.getDoctorList());
        JScrollPane doctorTableScrollPane = new JScrollPane(doctorTable);
        doctorTable.setBounds(0,0,200,400);
        doctorTable.setEnabled(false);//to prevent editing the table by user





        MyPanel header = new MyPanel(new Color(194, 169, 202),new Color(72, 151, 217));
        MyPanel footer = new MyPanel(new Color(207, 125, 227),new Color(207, 125, 227));
        MyPanel navigationButtons = new MyPanel(new Color(157, 200, 236),new Color(207, 125, 227));
        MyPanel addConsultation = new MyPanel(new Color(157, 200, 236),new Color(207, 125, 227));

        //Header
        header.setPreferredSize(new Dimension(600,50));
        MyJLabel headText = new MyJLabel(new Font("SansSerif", Font.BOLD,25),"Welcome to westminster skin Consultation Manager");
        header.add(headText);


        MyPanel footer1 = footer;
        footer.setPreferredSize(new Dimension(600,20));
        MyJLabel footerText = new MyJLabel(new Font("SansSerif", Font.BOLD,18),"   ");
        footer.add(footerText);

        //Navigation pane with buttons
        navigationButtons.setPreferredSize(new Dimension(250,600));
        navigationButtons.setLayout(new FlowLayout(FlowLayout.LEFT));

        navigationButtons.add(Box.createHorizontalStrut(65));
        navigationButtons.add(sortDoctors);
        navigationButtons.add(Box.createHorizontalStrut(65));
        navigationButtons.add(Box.createHorizontalStrut(65));
        navigationButtons.add(reviewConsultation);
        navigationButtons.add(Box.createHorizontalStrut(65));
        navigationButtons.add(Box.createHorizontalStrut(65));
        navigationButtons.add(saveData);
        navigationButtons.add(Box.createHorizontalStrut(65));
        navigationButtons.add(Box.createHorizontalStrut(65));
        navigationButtons.add(new MyButton("EXIT",Color.green));

        //add a consultation pane
        addConsultation.setPreferredSize(new Dimension(300,600));
        addConsultation.setLayout(new GridLayout(12,2,0,15));
        //Created a title for addConsultation
        //TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20,5,10,5), "Enter Your Details Below", TitledBorder.CENTER,TitledBorder.ABOVE_TOP,new Font("SansSerif", Font.PLAIN,20));//
        //addConsultation.setBorder(titledBorder);//
        //labels for user form
        //text fields for user form
        MyJLabel name_L = new MyJLabel(new Font("SansSerif", Font.PLAIN,12),BorderFactory.createEmptyBorder(10,10,10,10),"Enter your full name: ");
        MyJLabel mobileN_L = new MyJLabel(new Font("SansSerif", Font.PLAIN,12),BorderFactory.createEmptyBorder(10,10,10,10),"Enter your mobile number: ");
        MyJLabel ID_L = new MyJLabel(new Font("SansSerif", Font.PLAIN,12),BorderFactory.createEmptyBorder(10,10,10,10),"Enter your ID: ");
        MyJLabel image_L = new MyJLabel(new Font("SansSerif", Font.PLAIN,12),BorderFactory.createEmptyBorder(10,10,10,10),"Add a picture here: ");
        MyJLabel additional_L = new MyJLabel(new Font("SansSerif", Font.PLAIN,12),BorderFactory.createEmptyBorder(10,10,10,10),"Additional info: ");
        MyJLabel birth_L = new MyJLabel(new Font("SansSerif", Font.PLAIN,12),BorderFactory.createEmptyBorder(10,10,10,10),"Enter your date of birth: ");
        MyJLabel consultationDate_L = new MyJLabel(new Font("SansSerif", Font.PLAIN,12),BorderFactory.createEmptyBorder(10,10,10,10),"Enter consultation date: ");
        MyJLabel consultationTime_L = new MyJLabel(new Font("SansSerif", Font.PLAIN,12),BorderFactory.createEmptyBorder(10,10,10,10),"Enter consultation time: ");
        MyJLabel noOfHours_L = new MyJLabel(new Font("SansSerif", Font.PLAIN,12),BorderFactory.createEmptyBorder(10,10,10,10),"Enter number of hours: ");
        MyJLabel selectADoc_L = new MyJLabel(new Font("SansSerif", Font.PLAIN,12),BorderFactory.createEmptyBorder(10,10,10,10),"Select a doctor: ");

        fullName = new MyTextField();
        patientId = new MyTextField();
        additionalInfo = new TextArea();
        //formatted field for mobile number and no of hours
        initMobileNumberField();
        initNoOfHoursField();
        //date fields for user form
        UtilDateModel model1 = new UtilDateModel();
        JDatePanelImpl datePanel1 = new JDatePanelImpl(model1);
        UtilDateModel model2 = new UtilDateModel();
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2);
        consultationDate = new JDatePickerImpl(datePanel1);
        patientBirthDay = new JDatePickerImpl(datePanel2);
        //add a drop-down list to select a doctor
        selectionList = new JComboBox<>(manager.getComboList());
        //add action listeners for buttons
        browseImage.addActionListener(this);
        addConsultationB.addActionListener(this);
        sortDoctors.addActionListener(this);
        reviewConsultation.addActionListener(this);
        saveData.addActionListener(this);
        checkAvailability.addActionListener(this);
        //Time picker
        initTimeField();
        //adding all the user form components to the addConsultation panel
        addConsultation.add(selectADoc_L);
        addConsultation.add(selectionList);
        addConsultation.add(consultationDate_L);
        addConsultation.add(consultationDate);
        addConsultation.add(consultationTime_L);
        addConsultation.add(timeField);
        addConsultation.add(noOfHours_L);
        addConsultation.add(noOfHours);
        addConsultation.add(new JLabel());
        addConsultation.add(checkAvailability);
        addConsultation.add(name_L);
        addConsultation.add(fullName);
        addConsultation.add(mobileN_L);
        addConsultation.add(mobileNumber);
        addConsultation.add(birth_L);
        addConsultation.add(patientBirthDay);
        addConsultation.add(ID_L);
        addConsultation.add(patientId);
        addConsultation.add(image_L);
        addConsultation.add(browseImage);
        addConsultation.add(additional_L);
        addConsultation.add(additionalInfo);
        addConsultation.add(new JLabel());//added empty jLabel to skip space in grid
        addConsultation.add(addConsultationB);

        //Aligned 5 main panes using border layout
        this.add(header,BorderLayout.NORTH);
        this.add(navigationButtons,BorderLayout.WEST);
        this.add(doctorTableScrollPane,BorderLayout.CENTER);
        this.add(footer,BorderLayout.SOUTH);
        this.add(addConsultation,BorderLayout.EAST);

        //calling set visibility method after adding all the components cause some components didn't render correctly
        this.setVisible(true);
    }

    /*########################################################
    ################### Action Events ########################
    ########################################################*/

    @Override
    public void actionPerformed(ActionEvent e) {

        //if user clicked add consultation all the information will be retrieved
        if(e.getSource() == addConsultationB){

            //retrieve patient information

            //check if the patient name blank
            if(fullName.getText().isBlank()){
                JOptionPane.showMessageDialog(null, "Patient name is required", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }
            String [] tString = fullName.getText().split(" ");

            //check the first name and last name
            if(tString.length != 2){
                JOptionPane.showMessageDialog(null, "Please enter your first and last name like this 'firstName{SPACE}lastName'", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }
            String firstName = tString[0];
            String surName = tString[1];

            //check if the mobile number filed blank
            if(mobileNumber.getText().contains("#")){
                JOptionPane.showMessageDialog(null, "Mobile number is required", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }
            String mobileNumber = this.mobileNumber.getText();

            //check if the patient id field is filled
            if(patientId.getText().isBlank()){
                JOptionPane.showMessageDialog(null, "Patient ID is required", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }
            String patientId = this.patientId.getText();

            //check if the patient date of birth field is filled
            if(patientBirthDay.getModel().getValue() == null){
                JOptionPane.showMessageDialog(null, "Patient birthday is required", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }
            Date dateOfBirth = (Date) patientBirthDay.getModel().getValue();

            //retrieve consultation information
            String additionalInfo = this.additionalInfo.getText();

            //check if user entered a date
            if(consultationDate.getModel().getValue() == null){
                JOptionPane.showMessageDialog(null, "Date field is required", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }
            Date consultationDate = (Date) this.consultationDate.getModel().getValue();

            String idOfSelectedDoctor = selectionList.getSelectedItem().toString().split("-")[1];//get id of the doctor

            //check if the time filed is filled by user
            if(timeField.getText().contains("#")){
                JOptionPane.showMessageDialog(null, "Time filed is required", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }

            int hours = Integer.parseInt(timeField.getText().replaceAll("[^0-9]", "").substring(0,2));
            int minutes = Integer.parseInt(timeField.getText().replaceAll("[^0-9]", "").substring(2));

            //check if the entered time is valid
            if(hours > 23 || minutes > 59){
                JOptionPane.showMessageDialog(null, "Invalid time", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }
            LocalTime time = LocalTime.of(hours,minutes);//save time in local time object

            //check if the no of hours field is filled by user
            if(noOfHours.getText().contains("#")){
                JOptionPane.showMessageDialog(null, "No of hours filed is required", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }

            int noOfHours = Integer.parseInt(this.noOfHours.getText().replaceAll("[^0-9]", "").substring(0,2));

            //limited maximum consultation time to 10 hours per patient
            if(noOfHours > 10){
                JOptionPane.showMessageDialog(null, "Please enter a time shorter than 10 hours", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }

            //create patient object
            Patient patient = new Patient(patientId,firstName,surName,dateOfBirth,mobileNumber);
            //get the selected doctor object and save its reference
            Doctor doctor = manager.getDoctorByIndex(manager.searchDoctor(idOfSelectedDoctor));

            //return text of information about adding
            String massage = null;

            try {
                massage = consultationController.addConsultation(doctor,patient,consultationDate,time,additionalInfo,noOfHours,imageOfDisease);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(null, massage, "westminster",JOptionPane.INFORMATION_MESSAGE);
            clearFields();//clear the fields after adding consultation

        }

        //check availability
        if(e.getSource() == checkAvailability){

            //retrieve consultation information

            //check if user entered a date
            if(consultationDate.getModel().getValue() == null){
                JOptionPane.showMessageDialog(null, "Date field is required", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }
            Date consultationDate = (Date) this.consultationDate.getModel().getValue();
            String idOfSelectedDoctor = selectionList.getSelectedItem().toString().split("-")[1];//get id of the doctor

            //check if the time filed is filled by user
            if(timeField.getText().contains("#")){
                JOptionPane.showMessageDialog(null, "Time filed is required", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }

            int hours = Integer.parseInt(timeField.getText().replaceAll("[^0-9]", "").substring(0,2));
            int minutes = Integer.parseInt(timeField.getText().replaceAll("[^0-9]", "").substring(2));

            //check if the entered time is valid
            if(hours > 23 || minutes > 59){
                JOptionPane.showMessageDialog(null, "Invalid time", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }
            //save time in a time object
            LocalTime time = LocalTime.of(hours,minutes);

            //check if the no of hours field is filled by user
            if(noOfHours.getText().contains("#")){
                JOptionPane.showMessageDialog(null, "No of hours filed is required", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }
            int noOfHours = Integer.parseInt(this.noOfHours.getText().replaceAll("[^0-9]", "").substring(0,2));

            //limited maximum consultation time to 10 hours per patient
            if(noOfHours > 10){
                JOptionPane.showMessageDialog(null, "Please enter a time shorter than 10 hours", "westminster",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(consultationController.isAvailable(consultationDate,time,noOfHours,idOfSelectedDoctor)){
                JOptionPane.showMessageDialog(null, "Doctor is available", "westminster",JOptionPane.INFORMATION_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(null, "Doctor is unavailable", "westminster",JOptionPane.WARNING_MESSAGE);
            }
        }

        //select image from computer
        if(e.getSource() == browseImage){
            JFileChooser file = new JFileChooser();
            file.setCurrentDirectory(new File(""));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images","jpg","gif","png");
            file.addChoosableFileFilter(filter);
            int result = file.showSaveDialog(null);

            if(result == JFileChooser.APPROVE_OPTION){
                imageOfDisease = resizeImage(file.getSelectedFile().getAbsolutePath());
            } else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("Not selected!!!!!!!!!!!");
            }
        }
        //sort list of doctors
        if(e.getSource() == sortDoctors){

            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(doctorTable.getModel());
            doctorTable.setRowSorter(sorter);

            ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));//set column one as sort key and ascending order
            sorter.setSortKeys(sortKeys);
            doctorTable.repaint();//refresh the table after sorting
            JOptionPane.showMessageDialog(null, "physicians were successfully sorted by names.", "westminster",JOptionPane.INFORMATION_MESSAGE);
        }

        //review consultation
        if(e.getSource() == reviewConsultation){
            this.setVisible(false);
            new ReviewConsultationFrame(consultationController,this);

        }

        //save consultation data into a file
        if(e.getSource() == saveData){
            consultationController.saveData();
            JOptionPane.showMessageDialog(null,"Data successfully saved","westminster",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /*########################################################
    ####################### Methods ##########################
    ########################################################*/

    //resize the image to the size of the label

    private ImageIcon resizeImage(String imagePath){
        ImageIcon MyImage = new ImageIcon(imagePath);
        Image image = MyImage.getImage();
        Image newImage = image.getScaledInstance(480,300, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }

    //this method will create formatted text field to get time
    private void initTimeField(){
        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter("##h##min");//the # is for numeric values
            mask.setPlaceholderCharacter('#');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timeField = new JFormattedTextField(mask);//new text field that accepts only integer values
    }

    //this method will create formatted text field to get mobile number
    private void initMobileNumberField(){
        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter("##########");//the # is for numeric values
            mask.setPlaceholderCharacter('#');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mobileNumber = new JFormattedTextField(mask);//new text field that accepts only integer values
    }
    private void initNoOfHoursField(){
        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter("##h");//the # is for numeric values
            mask.setPlaceholderCharacter('#');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        noOfHours = new JFormattedTextField(mask);//new text field that accepts only integer values
    }

    //this method will clear fields after adding a patient
    private void clearFields(){
        fullName.setText("");
        mobileNumber.setText("");
        patientId.setText("");
        additionalInfo.setText("");
        timeField.setText("");
        noOfHours.setText("");
        imageOfDisease = new ImageIcon();
    }
}
