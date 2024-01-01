package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        String result = personService.addPerson(person);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{firstName}/{lastName}")
    public ResponseEntity<String> updatePerson(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody Person updatedPerson) {
        String result = personService.updatePerson(firstName, lastName, updatedPerson);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{firstName}/{lastName}")
    public ResponseEntity<String> deletePerson(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        String result = personService.deletePerson(firstName, lastName);
        return ResponseEntity.ok(result);
    }
}
