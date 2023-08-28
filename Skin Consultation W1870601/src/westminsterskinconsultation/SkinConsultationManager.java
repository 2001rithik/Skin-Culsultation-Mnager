package westminsterskinconsultation;

public interface SkinConsultationManager {
    void addDoctor(Doctor doctor);
    void deleteDoctor(String medicalLicenceNumber);
    void displayListOfDoctors();
    void saveInFile();
    void loadFromFile();
}
