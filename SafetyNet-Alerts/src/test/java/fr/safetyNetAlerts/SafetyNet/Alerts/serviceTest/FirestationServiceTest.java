package fr.safetyNetAlerts.SafetyNet.Alerts.serviceTest;

import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO.PersonCoveredByStationDTO;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.FireStation;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.EmergencyService;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.FirestationService;
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
public class FirestationServiceTest {

    @Mock
    private EmergencyService emergencyService;
    @InjectMocks
    private FirestationService firestationService;
    @Mock
    private PersonService personService;
    @Mock
    private MedicalrecordService medicalrecordService;


    @Test
    public void testAddFireStation() {
        FireStation testFireStation = new FireStation("1509 Culver St", "3");
        List<FireStation> fireStations = new ArrayList<>();
        when(emergencyService.getFirestations()).thenReturn(fireStations);

        String result = firestationService.addFireStation(testFireStation);

        assertEquals("Fire station added successfully", result);
        assertEquals(1, fireStations.size());
        assertEquals(testFireStation, fireStations.get(0));
    }

    @Test
    public void testUpdateFireStation() {
        String address = "1509 Culver St";
        FireStation updatedFireStation = new FireStation("1509 Culver St", "3");
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("1509 Culver St", "1"));
        when(emergencyService.getFirestations()).thenReturn(fireStations);

        String result = firestationService.updateFireStation(address, updatedFireStation);

        assertEquals("Fire station updated successfully", result);
        assertEquals(updatedFireStation.getStation(), fireStations.get(0).getStation());
    }

    @Test
    public void testUpdateFireStation_NotFound() {
        String address = "456 Elm St";
        FireStation updatedFireStation = new FireStation("456 Elm St", "3");
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("123 Main St", "1"));
        when(emergencyService.getFirestations()).thenReturn(fireStations);

        String result = firestationService.updateFireStation(address, updatedFireStation);

        assertEquals("Fire station not found", result);
        assertEquals(1, fireStations.size());
    }

    @Test
    public void testDeleteFireStation() {
        String address = "1509 Culver St";
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("1509 Culver St", "3"));
        when(emergencyService.getFirestations()).thenReturn(fireStations);

        String result = firestationService.deleteFireStation(address);

        assertEquals("Fire station deleted successfully", result);
        assertEquals(0, fireStations.size());
    }

    @Test
    public void testDeleteFireStation_NotFound() {
        String address = "456 Elm St";
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("123 Main St", "1"));
        when(emergencyService.getFirestations()).thenReturn(fireStations);

        String result = firestationService.deleteFireStation(address);

        assertEquals("Fire station not found", result);
        assertEquals(1, fireStations.size());
    }

    @Test
    public void testGetAddressesByStation() {
        int stationNumber = 1;
        List<FireStation> fireStations = createTestFireStationsList();

        when(firestationService.getFirestation()).thenReturn(fireStations);

        List<String> result = firestationService.getAddressesByStation(stationNumber);

        assertEquals(2, result.size());
        assertEquals("644 Gershwin Cir", result.get(0));
        assertEquals("947 E. Rose Dr", result.get(1));
    }

    @Test
    public void testGetStationNumbersByAddress() {
        String address = "644 Gershwin Cir";
        List<FireStation> fireStations = createTestFireStationsList();

        when(firestationService.getFirestation()).thenReturn(fireStations);

        String result = firestationService.getStationNumbersByAddress(address);

        assertEquals("1", result);
    }

    @Test
    public void testGetPersonCoveredByStation() {
        int stationNumber = 2;
        List<String> addressesCoveredByStation = Arrays.asList("1509 Culver St");
        List<Person> peopleCoveredByStation = createTestPeopleList();
        List<MedicalRecord> medicalRecords = createTestMedicalRecordsList();
        List<FireStation> fireStations = createTestFireStationsList();

        when(personService.getPeopleByAddresses(addressesCoveredByStation)).thenReturn(peopleCoveredByStation);
        when(medicalrecordService.getMedicalRecord()).thenReturn(medicalRecords);
        when(emergencyService.getFirestations()).thenReturn(fireStations);

        PersonCoveredByStationDTO result = firestationService.getPersonCoveredByStation(stationNumber);

        assertEquals(1, result.children());
        assertEquals(2, result.adults());
        assertEquals(3, result.people().size());
        assertEquals("John", result.people().get(0).firstName());
        assertEquals("Boyd", result.people().get(0).lastName());
        assertEquals("Jane", result.people().get(1).firstName());
        assertEquals("Boyd", result.people().get(1).lastName());
        assertEquals("Tenley", result.people().get(2).firstName());
        assertEquals("Boyd", result.people().get(2).lastName());
    }

    private List<FireStation> createTestFireStationsList() {
        return Arrays.asList(
                new FireStation("644 Gershwin Cir", "1"),
                new FireStation("947 E. Rose Dr", "1"),
                new FireStation("1509 Culver St", "2")
        );
    }

    private List<Person> createTestPeopleList() {
        return Arrays.asList(
                new Person("John", "Boyd", "1509 Culver St", "City", "12345", "123-456-7890", "john.doe@email.com"),
                new Person("Jane", "Boyd", "1509 Culver St", "City", "12345", "123-456-7890", "jane.doe@email.com"),
                new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com")
        );
    }

    private List<MedicalRecord> createTestMedicalRecordsList() {
        return Arrays.asList(
                new MedicalRecord("John", "Boyd", "03/01/2000", Arrays.asList("medication1"), Arrays.asList("allergy1")),
                new MedicalRecord("Jane", "Boyd", "05/15/1995", Arrays.asList("medication2"), Arrays.asList("allergy2")),
                new MedicalRecord("Tenley", "Boyd", "02/18/2012", Arrays.asList("medication3"), Arrays.asList("allergy2"))
        );
    }
}