package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.MedicalrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalrecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalrecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        String result = medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{firstName}/{lastName}")
    public ResponseEntity<String> updateMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody MedicalRecord updatedMedicalRecord) {
        String result = medicalRecordService.updateMedicalRecord(firstName, lastName, updatedMedicalRecord);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{firstName}/{lastName}")
    public ResponseEntity<String> deleteMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        String result = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        return ResponseEntity.ok(result);
    }
}