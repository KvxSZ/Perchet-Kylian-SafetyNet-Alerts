package fr.safetyNetAlerts.SafetyNet.Alerts.controllerTest;

import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.PersonController;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.PersonService;
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
public class PersonUnitControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @Test
    public void testAddPerson() {

        Person testPerson = new Person();
        when(personService.addPerson(testPerson)).thenReturn("successfully");


        ResponseEntity<String> response = personController.addPerson(testPerson);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully", response.getBody());
        verify(personService, times(1)).addPerson(testPerson);
    }

    @Test
    public void testUpdatePerson() {

        String firstName = "John";
        String lastName = "Boyd";
        Person updatedPerson = new Person();
        when(personService.updatePerson(firstName, lastName, updatedPerson)).thenReturn("successfully");


        ResponseEntity<String> response = personController.updatePerson(firstName, lastName, updatedPerson);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully", response.getBody());
        verify(personService, times(1)).updatePerson(firstName, lastName, updatedPerson);
    }

    @Test
    public void testDeletePerson() {

        String firstName = "John";
        String lastName = "Doe";
        when(personService.deletePerson(firstName, lastName)).thenReturn("successfully");


        ResponseEntity<String> response = personController.deletePerson(firstName, lastName);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully", response.getBody());
        verify(personService, times(1)).deletePerson(firstName, lastName);
    }
}