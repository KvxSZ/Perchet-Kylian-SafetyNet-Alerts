package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO.ResidentDetailsDAO;
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
import java.util.List;

@RestController
@RequestMapping("/flood")
public class FloodController {

    private final FirestationService fireStationService;
    private final PersonService personService;
    private final MedicalrecordService medicalRecordService;

    @Autowired
    public FloodController(FirestationService fireStationService, PersonService personService, MedicalrecordService medicalRecordService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/stations")
    public ResponseEntity<List<ResidentDetailsDAO>> getFloodStations(@RequestParam("stations") List<Integer> stationNumbers) {
        List<ResidentDetailsDAO> result = new ArrayList<>();

        for (Integer stationNumber : stationNumbers) {
            List<String> addressesCoveredByStation = fireStationService.getAddressesByStation(stationNumber);
            List<Person> residents = personService.getPeopleByAddresses(addressesCoveredByStation);

            for (Person resident : residents) {
                MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByFullName(resident.getFirstName(), resident.getLastName());


                ResidentDetailsDAO residentDetails = new ResidentDetailsDAO(resident.getFirstName() + " " + resident.getLastName(), resident.getPhone(), calculateAge(medicalRecord.getBirthdate()), medicalRecord.getMedications(), medicalRecord.getAllergies());

                String address = resident.getAddress();
                result.add(residentDetails);
            }
        }

        return ResponseEntity.ok(result);
    }

    private int calculateAge(String birthdateString) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthdate = LocalDate.parse(birthdateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return Period.between(birthdate, currentDate).getYears();
    }
}
