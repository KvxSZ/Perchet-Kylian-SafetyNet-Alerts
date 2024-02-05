package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.MedicalrecordService;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    private final PersonService personService;
    private final MedicalrecordService medicalRecordService;

    @Autowired
    public PersonInfoController(PersonService personService, MedicalrecordService medicalRecordService) {
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getPersonInfo(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {

        List<Map<String, Object>> result = new ArrayList<>();
        List<Person> matchingPeople = personService.getPeopleByFullName(firstName, lastName);

        for (Person person : matchingPeople) {
            Map<String, Object> personDetails = createPersonDetails(person);
            result.add(personDetails);
        }

        return ResponseEntity.ok(result);
    }

    public Map<String, Object> createPersonDetails(Person person) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());

        if (medicalRecord != null) {
            LocalDate currentDate = LocalDate.now();
            LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            int age = Period.between(birthdate, currentDate).getYears();

            Map<String, Object> personDetails = Map.of(
                    "name", person.getFirstName() + " " + person.getLastName(),
                    "address", person.getAddress(),
                    "email", person.getEmail(),
                    "age", age,
                    "medications", medicalRecord.getMedications(),
                    "allergies", medicalRecord.getAllergies()
            );

            return personDetails;
        } else {

            return null;
        }
    }


}
