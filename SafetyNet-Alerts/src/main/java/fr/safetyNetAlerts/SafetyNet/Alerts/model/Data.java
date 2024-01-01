package fr.safetyNetAlerts.SafetyNet.Alerts.model;

import java.util.List;

public record Data (

    List<Person> persons,
    List<FireStation> firestations,
    List<MedicalRecord> medicalrecords

){}
