package fr.safetyNetAlerts.SafetyNet.Alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.Data;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.FireStation;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmergencyService {

    @Autowired
    private ObjectMapper objectMapper;
    private List<Person> persons = new ArrayList<>();
    private List<FireStation> firestations = new ArrayList<>();
    private List<MedicalRecord> medicalrecords = new ArrayList<>();
    public void readJsonFile(){
        ObjectMapper mapper = new ObjectMapper();

        try {
            URL res = getClass().getClassLoader().getResource("data.json");
            File file = Paths.get(res.toURI()).toFile();

            Data data = mapper.readValue(file, Data.class);

            persons = data.persons();
            firestations = data.firestations();
            medicalrecords = data.medicalrecords();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<FireStation> getFirestations() {
        return firestations;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalrecords;
    }

}
