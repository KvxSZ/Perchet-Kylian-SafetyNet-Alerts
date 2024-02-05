package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.FirestationService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fire")
public class FireController {

    private final FirestationService fireStationService;
    private final PersonService personService;
    private final MedicalrecordService medicalRecordService;

    @Autowired
    public FireController(FirestationService fireStationService, PersonService personService, MedicalrecordService medicalRecordService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getResidentsAndFireStationByAddress(@RequestParam("address") String address) {
        Map<String, Object> result = new HashMap<>();

        List<Person> residents = personService.getResidentsByAddress(address);
        if (residents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String fireStationNumbers = fireStationService.getStationNumbersByAddress(address);

        List<Map<String, Object>> residentsInfo = new ArrayList<>();
        for (Person resident : residents) {
            Map<String, Object> residentDetails = new HashMap<>();
            residentDetails.put("name", resident.getFirstName() + " " + resident.getLastName());
            residentDetails.put("phone", resident.getPhone());

            MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByFullName(resident.getFirstName(), resident.getLastName());
            if (medicalRecord != null) {
                residentDetails.put("age", calculateAge(medicalRecord.getBirthdate()));
                residentDetails.put("medications", medicalRecord.getMedications());
                residentDetails.put("allergies", medicalRecord.getAllergies());
            }
            residentsInfo.add(residentDetails);
        }

        result.put("residents", residentsInfo);
        result.put("fireStationNumbers", fireStationNumbers);

        return ResponseEntity.ok(result);
    }

    private int calculateAge(String birthdateString) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthdate = LocalDate.parse(birthdateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return Period.between(birthdate, currentDate).getYears();
    }
}
