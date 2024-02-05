package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    private final PersonService personService;
    private Logger logger = LoggerFactory.getLogger(CommunityEmailController.class);

    public CommunityEmailController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getCommunityEmails(@RequestParam("city") String city) {
        List<String> emails = new ArrayList<>();

        for (Person person : personService.getPerson()) {
            if (person.getCity().equalsIgnoreCase(city)) {
                emails.add(person.getEmail());
            }
        }

        if (!emails.isEmpty()) {
            logger.info("Data successfully recovered");
            return ResponseEntity.ok(emails);
        } else {
            logger.error("Failed data recovery");
            return ResponseEntity.notFound().build();
        }
    }
}
