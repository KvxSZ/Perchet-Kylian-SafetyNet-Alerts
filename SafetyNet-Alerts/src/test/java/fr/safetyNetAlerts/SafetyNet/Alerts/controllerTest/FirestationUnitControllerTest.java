package fr.safetyNetAlerts.SafetyNet.Alerts.controllerTest;

import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.FirestationController;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.FireStation;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.FirestationService;
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
public class FirestationUnitControllerTest {

    @Mock
    private FirestationService firestationService;

    @InjectMocks
    private FirestationController firestationController;

    @Test
    public void testAddFireStation() {

        FireStation testFireStation = new FireStation();
        when(firestationService.addFireStation(testFireStation)).thenReturn("successfully");


        ResponseEntity<String> response = firestationController.addFireStation(testFireStation);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully", response.getBody());
        verify(firestationService, times(1)).addFireStation(testFireStation);
    }

    @Test
    public void testUpdateFireStation() {

        String address = "123 Main St";
        FireStation updatedFireStation = new FireStation();
        when(firestationService.updateFireStation(address, updatedFireStation)).thenReturn("successfully");


        ResponseEntity<String> response = firestationController.updateFireStation(address, updatedFireStation);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully", response.getBody());
        verify(firestationService, times(1)).updateFireStation(address, updatedFireStation);
    }

    @Test
    public void testDeleteFireStation() {

        String address = "1509 Culver St";
        when(firestationService.deleteFireStation(address)).thenReturn("successfully");

        ResponseEntity<String> response = firestationController.deleteFireStation(address);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully", response.getBody());
        verify(firestationService, times(1)).deleteFireStation(address);
    }
}