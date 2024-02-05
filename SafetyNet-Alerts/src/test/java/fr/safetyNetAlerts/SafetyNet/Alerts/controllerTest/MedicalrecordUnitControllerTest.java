package fr.safetyNetAlerts.SafetyNet.Alerts.controllerTest;

import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.MedicalRecordController;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.MedicalrecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalrecordUnitControllerTest {

    @Mock
    private MedicalrecordService medicalrecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @Test
    public void testAddMedicalRecord() {

        MedicalRecord testMedicalRecord = new MedicalRecord();
        when(medicalrecordService.addMedicalRecord(testMedicalRecord)).thenReturn("successfully");


        ResponseEntity<String> response = medicalRecordController.addMedicalRecord(testMedicalRecord);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully", response.getBody());
        verify(medicalrecordService, times(1)).addMedicalRecord(testMedicalRecord);
    }

    @Test
    public void testUpdateMedicalRecord() {

        String firstName = "John";
        String lastName = "Doe";
        MedicalRecord updatedMedicalRecord = new MedicalRecord(/* Initialize updated medical record data */);
        when(medicalrecordService.updateMedicalRecord(firstName, lastName, updatedMedicalRecord)).thenReturn("successfully");


        ResponseEntity<String> response = medicalRecordController.updateMedicalRecord(firstName, lastName, updatedMedicalRecord);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully", response.getBody());
        verify(medicalrecordService, times(1)).updateMedicalRecord(firstName, lastName, updatedMedicalRecord);
    }

    @Test
    public void testDeleteMedicalRecord() {

        String firstName = "John";
        String lastName = "Doe";
        when(medicalrecordService.deleteMedicalRecord(firstName, lastName)).thenReturn("successfully");

        ResponseEntity<String> response = medicalRecordController.deleteMedicalRecord(firstName, lastName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully", response.getBody());
        verify(medicalrecordService, times(1)).deleteMedicalRecord(firstName, lastName);
    }
}
