package fr.safetyNetAlerts.SafetyNet.Alerts.serviceTest;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.FireStation;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.EmergencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class EmergencyServiceTest {

    private EmergencyService emergencyService;

    @BeforeEach
    public void setUp() {
        emergencyService = new EmergencyService();
        emergencyService.readJsonFile();
    }

    @Test
    public void testGetPersons() {
        List<Person> persons = emergencyService.getPersons();
        Assertions.assertFalse(persons.isEmpty());
        Person firstPerson = persons.get(0);
        Assertions.assertEquals("John", firstPerson.getFirstName());
        Assertions.assertEquals("Boyd", firstPerson.getLastName());
        Assertions.assertEquals("1509 Culver St", firstPerson.getAddress());
    }

    @Test
    public void testGetFirestations() {
        List<FireStation> firestations = emergencyService.getFirestations();
        Assertions.assertFalse(firestations.isEmpty());
        FireStation firstFirestation = firestations.get(0);
        Assertions.assertEquals("1509 Culver St", firstFirestation.getAddress());
        Assertions.assertEquals("3", firstFirestation.getStation());
    }

    @Test
    public void testGetMedicalRecords() {
        List<MedicalRecord> medicalRecords = emergencyService.getMedicalRecords();
        Assertions.assertFalse(medicalRecords.isEmpty());
        MedicalRecord firstMedicalRecord = medicalRecords.get(0);
        Assertions.assertEquals("John", firstMedicalRecord.getFirstName());
        Assertions.assertEquals("Boyd", firstMedicalRecord.getLastName());
        Assertions.assertEquals("03/06/1984", firstMedicalRecord.getBirthdate());
    }



}
