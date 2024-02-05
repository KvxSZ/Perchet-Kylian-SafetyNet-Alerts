package fr.safetyNetAlerts.SafetyNet.Alerts.serviceTest;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.EmergencyService;
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
public class PersonServiceTest {


    @Mock
    private EmergencyService emergencyService;
    @InjectMocks
    private PersonService personService;


    @Test
    public void testAddPerson() {

        Person testPerson = new Person();
        List<Person> persons = new ArrayList<>();
        when(emergencyService.getPersons()).thenReturn(persons);


        String result = personService.addPerson(testPerson);


        assertEquals("Person added successfully", result);
        assertEquals(1, persons.size());
        assertEquals(testPerson, persons.get(0));
    }

    @Test
    public void testUpdatePerson() {

        String firstName = "John";
        String lastName = "Boyd";
        Person updatedPerson = new Person("John", "Boyd", "1509 NY St", "NY", "10010", "857-874-6512", "jadoe@email.com");
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        when(emergencyService.getPersons()).thenReturn(persons);


        String result = personService.updatePerson(firstName, lastName, updatedPerson);


        assertEquals("Person updated successfully", result);
        assertEquals(updatedPerson.getAddress(), persons.get(0).getAddress());
        assertEquals(updatedPerson.getCity(), persons.get(0).getCity());
        assertEquals(updatedPerson.getZip(), persons.get(0).getZip());
        assertEquals(updatedPerson.getPhone(), persons.get(0).getPhone());
        assertEquals(updatedPerson.getEmail(), persons.get(0).getEmail());

    }

    @Test
    public void testDeletePerson() {

        String firstName = "John";
        String lastName = "Boyd";
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        when(emergencyService.getPersons()).thenReturn(persons);


        String result = personService.deletePerson(firstName, lastName);


        assertEquals("Person deleted successfully", result);
        assertEquals(0, persons.size());
    }

    @Test
    public void testDeletePerson_NotFound() {

        String firstName = "Jane";
        String lastName = "Boyd";
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        when(emergencyService.getPersons()).thenReturn(persons);

        String result = personService.deletePerson(firstName, lastName);

        assertEquals("Person not found", result);
        assertEquals(1, persons.size());
    }

    @Test
    public void testGetPeopleByAddresses() {
        List<String> addresses = Arrays.asList("123 Main St", "456 Elm St");
        List<Person> allPeople = createTestPeopleList();

        when(personService.getPerson()).thenReturn(allPeople);

        List<Person> result = personService.getPeopleByAddresses(addresses);

        assertEquals(3, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        assertEquals("Bob", result.get(2).getFirstName());
    }

    @Test
    public void testGetResidentsByAddress() {
        String address = "123 Main St";
        List<Person> allPeople = createTestPeopleList();

        when(personService.getPerson()).thenReturn(allPeople);

        List<Person> result = personService.getResidentsByAddress(address);

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Bob", result.get(1).getFirstName());
    }

    @Test
    public void testGetPeopleByFullName() {
        String firstName = "John";
        String lastName = "Doe";
        List<Person> allPeople = createTestPeopleList();

        when(personService.getPerson()).thenReturn(allPeople);

        List<Person> result = personService.getPeopleByFullName(firstName, lastName);

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
    }

    private List<Person> createTestPeopleList() {
        return Arrays.asList(
                new Person("John", "Doe", "123 Main St", "City1", "12345", "123-456-7890", "john.doe@email.com"),
                new Person("Jane", "Doe", "456 Elm St", "City2", "67890", "987-654-3210", "jane.doe@email.com"),
                new Person("Bob", "Smith", "123 Main St", "City1", "12345", "111-222-3333", "bob.smith@email.com")
        );
    }

}
