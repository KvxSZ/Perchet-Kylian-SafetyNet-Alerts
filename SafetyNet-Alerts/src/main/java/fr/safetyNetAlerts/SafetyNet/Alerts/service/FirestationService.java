package fr.safetyNetAlerts.SafetyNet.Alerts.service;

import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO.PersonCoveredByStationDTO;
import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO.PersonDTO;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.FireStation;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FirestationService {


    private final EmergencyService emergencyService;
    private final MedicalrecordService medicalRecordService;
    private final PersonService personService;

    public FirestationService(EmergencyService emergencyService, MedicalrecordService medicalRecordService, PersonService personService) {
        this.emergencyService = emergencyService;
        this.medicalRecordService = medicalRecordService;
        this.personService = personService;
    }

    public List<FireStation> getFirestation() {
        return emergencyService.getFirestations();
    }

    public String addFireStation(FireStation newFireStation) {
        List<FireStation> fireStations = emergencyService.getFirestations();
        fireStations.add(newFireStation);
        return "Fire station added successfully";
    }

    public String updateFireStation(String address, FireStation updatedFireStation) {
        List<FireStation> fireStations = emergencyService.getFirestations();
        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(address)) {
                fireStation.setStation(updatedFireStation.getStation());
                return "Fire station updated successfully";
            }
        }
        return "Fire station not found";
    }

    public String deleteFireStation(String address) {
        List<FireStation> fireStations = emergencyService.getFirestations();
        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(address)) {
                fireStations.remove(fireStation);
                return "Fire station deleted successfully";
            }
        }
        return "Fire station not found";
    }

    public List<String> getAddressesByStation(int stationNumber) {
        List<String> addresses = new ArrayList<>();

        List<FireStation> fireStations = getFirestation();
        for (FireStation fireStation : fireStations) {
            if (fireStation.getStation().equals(String.valueOf(stationNumber))) {
                addresses.add(fireStation.getAddress());
            }
        }

        return addresses;
    }

    public String getStationNumbersByAddress(String address) {
        List<FireStation> fireStations = getFirestation();

        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(address)) {
                return fireStation.getStation();
            }
        }
        return "Station not found";
    }

    public PersonCoveredByStationDTO getPersonCoveredByStation(int stationNumber) {

        List<String> addressesCoveredByStation = getAddressesByStation(stationNumber);
        List<Person> peopleCoveredByStation = personService.getPeopleByAddresses(addressesCoveredByStation);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecord();
        int adultCount = 0;
        int childCount = 0;
        List<PersonDTO> peopleDetails = new ArrayList<>();

        for (Person person : peopleCoveredByStation) {
            PersonDTO details = new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());

            MedicalRecord medicalRecord = medicalRecords.stream()
                    .filter(record -> record.getFirstName().equals(person.getFirstName()) &&
                            record.getLastName().equals(person.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (medicalRecord != null) {
                LocalDate currentDate = LocalDate.now();
                LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                int age = Period.between(birthdate, currentDate).getYears();

                if (age <= 18) {
                    childCount++;
                } else {
                    adultCount++;
                }
            }

            peopleDetails.add(details);
        }

        return new PersonCoveredByStationDTO(childCount, adultCount, peopleDetails);
    }

}
