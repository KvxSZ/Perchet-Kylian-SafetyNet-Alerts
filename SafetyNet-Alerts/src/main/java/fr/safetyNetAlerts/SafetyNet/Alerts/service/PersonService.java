package fr.safetyNetAlerts.SafetyNet.Alerts.service;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonService {

    private final EmergencyService emergencyService;

    public PersonService(EmergencyService emergencyService) {
        this.emergencyService = emergencyService;
    }

    public String addPerson(Person newPerson) {
        List<Person> persons = emergencyService.getPersons();

        persons.add(newPerson);
        return "Person added successfully";
    }

    public String updatePerson(String firstName, String lastName, Person updatedPerson) {
        List<Person> persons = emergencyService.getPersons();
        for (Person person : persons) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {

                person.setAddress(updatedPerson.getAddress());
                person.setCity(updatedPerson.getCity());
                person.setEmail(updatedPerson.getEmail());
                person.setPhone(updatedPerson.getPhone());
                person.setZip(updatedPerson.getZip());
                return "Person updated successfully";
            }
        }
        return "Person not found";
    }

    public String deletePerson(String firstName, String lastName) {
        List<Person> persons = emergencyService.getPersons();
        for (Person person : persons) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {

                persons.remove(person);
                return "Person deleted successfully";
            }
        }
        return "Person not found";
    }
}