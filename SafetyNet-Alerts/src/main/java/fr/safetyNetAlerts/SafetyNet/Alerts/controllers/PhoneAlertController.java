package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.FirestationService;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

    private final FirestationService fireStationService;
    private final PersonService personService;
    private Logger logger = LoggerFactory.getLogger(PhoneAlertController.class);

    @Autowired
    public PhoneAlertController(FirestationService fireStationService, PersonService personService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getPhoneAlert(@RequestParam("firestation") int firestationNumber) {
        List<String> phoneNumbers = new ArrayList<>();

        List<String> addressesCoveredByStation = fireStationService.getAddressesByStation(firestationNumber);
        List<Person> residentsCoveredByStation = personService.getPeopleByAddresses(addressesCoveredByStation);

        for (Person resident : residentsCoveredByStation) {
            phoneNumbers.add(resident.getPhone());
        }
        if (!phoneNumbers.isEmpty()) {
            logger.info("Data successfully recovered");
            return ResponseEntity.ok(phoneNumbers);
        } else {
            logger.error("Failed data recovery");
            return ResponseEntity.notFound().build();
        }

    }
}
