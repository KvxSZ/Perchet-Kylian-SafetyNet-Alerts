package fr.safetyNetAlerts.SafetyNet.Alerts.service;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalrecordService {

    private final EmergencyService emergencyService;

    public MedicalrecordService(EmergencyService emergencyService) {
        this.emergencyService = emergencyService;
    }

    public String addMedicalRecord(MedicalRecord newMedicalRecord) {
        List<MedicalRecord> medicalRecords = emergencyService.getMedicalRecords();
        medicalRecords.add(newMedicalRecord);
        return "Medical record added successfully";
    }

    public String updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedMedicalRecord) {
        List<MedicalRecord> medicalRecords = emergencyService.getMedicalRecords();
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
                medicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
                medicalRecord.setMedications(updatedMedicalRecord.getMedications());
                medicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
                return "Medical record updated successfully";
            }
        }
        return "Medical record not found";
    }

    public String deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> medicalRecords = emergencyService.getMedicalRecords();
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
                medicalRecords.remove(medicalRecord);
                return "Medical record deleted successfully";
            }
        }
        return "Medical record not found";
    }
}