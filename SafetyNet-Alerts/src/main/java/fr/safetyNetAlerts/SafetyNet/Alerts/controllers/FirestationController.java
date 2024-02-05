package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO.PersonCoveredByStationDTO;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.FireStation;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.FirestationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationService fireStationService;
    private Logger logger = LoggerFactory.getLogger(FirestationController.class);


    @Autowired
    public FirestationController(FirestationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("/firestation")
    public List<FireStation> getFirestation() {
        return fireStationService.getFirestation();
    }


    @PostMapping
    public ResponseEntity<String> addFireStation(@RequestBody FireStation fireStation) {
        String result = fireStationService.addFireStation(fireStation);
        if (result.contains("successfully")) {
            logger.info(result);
            return ResponseEntity.ok(result);
        } else {
            logger.error("Failed to add a FireStation");
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> updateFireStation(
            @RequestParam("address") String address,
            @RequestBody FireStation updatedFireStation) {
        String result = fireStationService.updateFireStation(address, updatedFireStation);
        if (result.contains("successfully")) {
            logger.info(result);
            return ResponseEntity.ok(result);
        } else {
            logger.error(result);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFireStation(@RequestParam("address") String address) {
        String result = fireStationService.deleteFireStation(address);
        if (result.contains("successfully")) {
            logger.info(result);
            return ResponseEntity.ok(result);
        } else {
            logger.error(result);
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<PersonCoveredByStationDTO> getPeopleCoveredByStation(@RequestParam("stationNumber") int stationNumber) {

        if (fireStationService.getAddressesByStation(stationNumber) != null) {
            logger.info("People successfully recovered");
            return ResponseEntity.ok(fireStationService.getPersonCoveredByStation(stationNumber));
        } else {
            logger.error("Unsuccessful people recovery");
            return ResponseEntity.notFound().build();
        }

    }

    private int calculateAge(String birthdateString) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthdate = LocalDate.parse(birthdateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return Period.between(birthdate, currentDate).getYears();
    }
}