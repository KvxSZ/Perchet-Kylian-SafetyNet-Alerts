package fr.safetyNetAlerts.SafetyNet.Alerts.service;

import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO.ChildAndOtherResident;
import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO.ResidentInfoDAO;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalrecordService {

    private final EmergencyService emergencyService;
    private final PersonService personService;

    @Autowired
    public MedicalrecordService(EmergencyService emergencyService, PersonService personService) {
        this.emergencyService = emergencyService;
        this.personService = personService;
    }

    public List<MedicalRecord> getMedicalRecord() {
        return emergencyService.getMedicalRecords();
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

    public MedicalRecord getMedicalRecordByFullName(String firstName, String lastName) {
        List<MedicalRecord> medicalRecords = getMedicalRecord();

        Optional<MedicalRecord> optionalMedicalRecord = medicalRecords.stream()
                .filter(record -> record.getFirstName().equals(firstName) && record.getLastName().equals(lastName))
                .findFirst();

        return optionalMedicalRecord.orElse(null);
    }

    public MedicalRecord findMedicalRecord(Person person) {
        return getMedicalRecord().stream()
                .filter(record -> record.getFirstName().equals(person.getFirstName()) &&
                        record.getLastName().equals(person.getLastName()))
                .findFirst()
                .orElse(null);
    }

    public ChildAndOtherResident getChildAndOtherResident(String address) {
        List<Person> residents = personService.getPerson();

        List<ResidentInfoDAO> childrenInfo = new ArrayList<>();
        List<ResidentInfoDAO> otherResidentsInfo = new ArrayList<>();
        List<Person> residentsAtAddress = new ArrayList<>();

        for (Person person : residents) {
            if (person.getAddress().equals(address)) {
                residentsAtAddress.add(person);
            }
        }

        for (Person resident : residentsAtAddress) {
            MedicalRecord medicalRecord = findMedicalRecord(resident);

            if (medicalRecord != null) {
                LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                int age = Period.between(birthdate, LocalDate.now()).getYears();
                ResidentInfoDAO residentInfo = new ResidentInfoDAO(resident.getFirstName(), resident.getLastName(), age);

                if (age <= 18) {
                    childrenInfo.add(residentInfo);
                } else {
                    otherResidentsInfo.add(residentInfo);
                }
            }
        }
        if (!childrenInfo.isEmpty()) {
            return new ChildAndOtherResident(childrenInfo, otherResidentsInfo);
        }
        return null;
    }
}