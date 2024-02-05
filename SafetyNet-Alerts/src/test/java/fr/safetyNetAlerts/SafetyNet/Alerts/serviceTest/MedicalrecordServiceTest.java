package fr.safetyNetAlerts.SafetyNet.Alerts.serviceTest;

import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO.ChildAndOtherResident;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.EmergencyService;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.MedicalrecordService;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalrecordServiceTest {

    @Mock
    private EmergencyService emergencyService;
    @InjectMocks
    private MedicalrecordService medicalrecordService;
    @Mock
    private PersonService personService;

    @Test
    public void testAddMedicalRecord() {
        MedicalRecord testMedicalRecord = new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"));
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        when(emergencyService.getMedicalRecords()).thenReturn(medicalRecords);

        String result = medicalrecordService.addMedicalRecord(testMedicalRecord);

        assertEquals("Medical record added successfully", result);
        assertEquals(1, medicalRecords.size());
        assertEquals(testMedicalRecord, medicalRecords.get(0));
    }

    @Test
    public void testUpdateMedicalRecord() {
        String firstName = "John";
        String lastName = "Boyd";
        MedicalRecord updatedMedicalRecord = new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:200mg"), List.of("nillacilan"));
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")));
        when(emergencyService.getMedicalRecords()).thenReturn(medicalRecords);

        String result = medicalrecordService.updateMedicalRecord(firstName, lastName, updatedMedicalRecord);

        assertEquals("Medical record updated successfully", result);
        assertEquals(updatedMedicalRecord.getMedications(), medicalRecords.get(0).getMedications());
    }

    @Test
    public void testUpdateMedicalRecord_NotFound() {
        String firstName = "Jane";
        String lastName = "Doe";
        MedicalRecord updatedMedicalRecord = new MedicalRecord("Jane", "Doe", "01/01/1990", List.of("medication:100mg"), List.of("allergy"));
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")));
        when(emergencyService.getMedicalRecords()).thenReturn(medicalRecords);

        String result = medicalrecordService.updateMedicalRecord(firstName, lastName, updatedMedicalRecord);

        assertEquals("Medical record not found", result);
        assertEquals(1, medicalRecords.size());
    }

    @Test
    public void testDeleteMedicalRecord() {
        String firstName = "John";
        String lastName = "Boyd";
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")));
        when(emergencyService.getMedicalRecords()).thenReturn(medicalRecords);

        String result = medicalrecordService.deleteMedicalRecord(firstName, lastName);

        assertEquals("Medical record deleted successfully", result);
        assertEquals(0, medicalRecords.size());
    }

    @Test
    public void testDeleteMedicalRecord_NotFound() {
        String firstName = "Jane";
        String lastName = "Doe";
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")));
        when(emergencyService.getMedicalRecords()).thenReturn(medicalRecords);

        String result = medicalrecordService.deleteMedicalRecord(firstName, lastName);

        assertEquals("Medical record not found", result);
        assertEquals(1, medicalRecords.size());
    }

    @Test
    public void testGetMedicalRecordByFullName() {
        String firstName = "John";
        String lastName = "Doe";
        List<MedicalRecord> medicalRecords = createTestMedicalRecordsList();

        when(medicalrecordService.getMedicalRecord()).thenReturn(medicalRecords);

        MedicalRecord result = medicalrecordService.getMedicalRecordByFullName(firstName, lastName);

        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    public void testFindMedicalRecord() {
        Person person = new Person("Jane", "Doe", "123 Main St", "City", "12345", "123-456-7890", "jane.doe@email.com");
        List<MedicalRecord> medicalRecords = createTestMedicalRecordsList();

        when(medicalrecordService.getMedicalRecord()).thenReturn(medicalRecords);

        MedicalRecord result = medicalrecordService.findMedicalRecord(person);

        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    public void testGetChildAndOtherResident() {
        String address = "123 Main St";
        List<Person> residents = createTestResidentsList();
        List<MedicalRecord> medicalRecords = createTestMedicalRecordsList();

        when(personService.getPerson()).thenReturn(residents);
        when(medicalrecordService.getMedicalRecord()).thenReturn(medicalRecords);

        ChildAndOtherResident result = medicalrecordService.getChildAndOtherResident(address);

        assertEquals(1, result.childrenInfo().size());
        assertEquals(1, result.otherResidentsMenbers().size());
        assertEquals("John", result.childrenInfo().get(0).firstName());
        assertEquals("Doe", result.childrenInfo().get(0).lastName());
        assertEquals("Jane", result.otherResidentsMenbers().get(0).firstName());
        assertEquals("Doe", result.otherResidentsMenbers().get(0).lastName());
    }

    private List<MedicalRecord> createTestMedicalRecordsList() {
        return Arrays.asList(
                new MedicalRecord("John", "Doe", "03/01/2015", Arrays.asList("medication1"), Arrays.asList("allergy1")),
                new MedicalRecord("Jane", "Doe", "05/15/1995", Arrays.asList("medication2"), Arrays.asList("allergy2"))
        );
    }

    private List<Person> createTestResidentsList() {
        return Arrays.asList(
                new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@email.com"),
                new Person("Jane", "Doe", "123 Main St", "City", "12345", "123-456-7890", "jane.doe@email.com")
        );
    }

}