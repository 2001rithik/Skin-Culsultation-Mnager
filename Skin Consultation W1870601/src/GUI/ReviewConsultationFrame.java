package GUI;

import westminsterskinconsultation.Consultation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

public class ReviewConsultationFrame extends JFrame implements ActionListener {

    private ConsultationController consultationController;
    private JButton submit;
    private JButton goBack;
    private JTextField consultationId;
    private JLabel imageLabel;

    private JLabel consultationID_L;
    private JLabel patientName_L;
    private JLabel doctorName_L;
    private JLabel date_L;
    private JLabel time_L;
    private JLabel noOfHours_L;
    private JLabel cost_L;
    private JLabel notes_L;
    private MainFrame mainFrame;//used this as reference to return to main page
    public ReviewConsultationFrame(ConsultationController consultationController,MainFrame mainFrame)  {

        this.consultationController = consultationController;
        this.mainFrame = mainFrame;

        MyJLabel patientID_L = new MyJLabel(new Font("SansSerif", Font.PLAIN,14),BorderFactory.createEmptyBorder(0,0,0,10),"Enter your Consultation ID: ");
        JPanel description = new JPanel(new GridLayout(8,1,0,2));
        submit = new JButton("Submit");
        goBack = new JButton("Back");
        consultationId = new JTextField();
        imageLabel = new JLabel();

        consultationID_L = new JLabel();
        patientName_L = new JLabel();
        doctorName_L = new JLabel();
        date_L = new JLabel();
        time_L = new JLabel();
        noOfHours_L = new JLabel();
        cost_L = new JLabel();
        notes_L = new JLabel();

        description.add(consultationID_L);
        description.add(patientName_L);
        description.add(doctorName_L);
        description.add(date_L);
        description.add(time_L);
        description.add(noOfHours_L);
        description.add(cost_L);
        description.add(notes_L);

        patientID_L.setBounds(10,10,190,20);
        consultationId.setBounds(200,10,150,25);
        submit.setBounds(200,50,90,30);
        goBack.setBounds(200,85,90,30);
        imageLabel.setBounds(400,220,360,160);
        description.setBounds(400,10,400,200);
        submit.addActionListener(this);
        goBack.addActionListener(this);

        this.add(patientID_L);
        this.add(consultationId);
        this.add(submit);
        this.add(goBack);
        this.add(imageLabel);
        this.add(description);

        this.setSize(800,500);
        this.setLayout(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){
            Consultation consultation = consultationController.getConsultation(consultationId.getText());
            if(consultation != null){
                String [] consultationInformation = consultation.toStringArray();

                //imageLabel.setIcon(consultation.getDiseasedAreaImage());

                try {
                    imageLabel.setIcon(consultation.getDecryptedImage());
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                consultationID_L.setText(consultationInformation[0]);
                patientName_L.setText(consultationInformation[1]);
                doctorName_L.setText(consultationInformation[2]);
                date_L.setText(consultationInformation[3]);
                time_L.setText(consultationInformation[4]);
                noOfHours_L.setText(consultationInformation[5]);
                cost_L.setText(consultationInformation[6]);
                notes_L.setText(consultationInformation[7]);

            }else {
                JOptionPane.showMessageDialog(null,"Invalid consultation ID","westminster",JOptionPane.WARNING_MESSAGE);
            }

        }
        if(e.getSource() == goBack){
            this.dispose();
            mainFrame.setVisible(true);
        }
    }
}
