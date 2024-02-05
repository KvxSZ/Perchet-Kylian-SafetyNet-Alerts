package fr.safetyNetAlerts.SafetyNet.Alerts.service;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PersonService {

    private final EmergencyService emergencyService;

    public PersonService(EmergencyService emergencyService) {
        this.emergencyService = emergencyService;
    }

    public List<Person> getPerson() {
        return emergencyService.getPersons();
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

    public List<Person> getPeopleByAddresses(List<String> addressesCoveredByStation) {
        List<Person> allPeople = getPerson();
        List<Person> peopleByAddresses = new ArrayList<>();

        for (Person person : allPeople) {
            if (addressesCoveredByStation.contains(person.getAddress())) {
                peopleByAddresses.add(person);
            }
        }

        return peopleByAddresses;
    }

    public List<Person> getResidentsByAddress(String address) {
        List<Person> allPeople = getPerson();
        List<Person> peopleByAddresses = new ArrayList<>();

        for (Person person : allPeople) {
            if (person.getAddress().equals(address)) {
                peopleByAddresses.add(person);
            }
        }

        return peopleByAddresses;
    }

    public List<Person> getPeopleByFullName(String firstName, String lastName) {
        List<Person> matchingPeople = new ArrayList<>();

        for (Person person : getPerson()) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                matchingPeople.add(person);
            }
        }

        return matchingPeople;
    }

}