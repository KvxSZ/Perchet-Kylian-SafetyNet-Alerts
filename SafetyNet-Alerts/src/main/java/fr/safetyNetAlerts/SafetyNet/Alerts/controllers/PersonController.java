package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPerson() {
        return personService.getPerson();
    }

    @PostMapping
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        String result = personService.addPerson(person);
        if (result.contains("successfully")) {
            logger.info(result);
            return ResponseEntity.ok(result);
        } else {
            logger.error("Failed to add a person");
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> updatePerson(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestBody Person updatedPerson) {
        String result = personService.updatePerson(firstName, lastName, updatedPerson);
        if (result.contains("successfully")) {
            logger.info(result);
            return ResponseEntity.ok(result);
        } else {
            logger.error(result);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        String result = personService.deletePerson(firstName, lastName);
        if (result.contains("successfully")) {
            logger.info(result);
            return ResponseEntity.ok(result);
        } else {
            logger.error(result);
            return ResponseEntity.notFound().build();
        }
    }
}
