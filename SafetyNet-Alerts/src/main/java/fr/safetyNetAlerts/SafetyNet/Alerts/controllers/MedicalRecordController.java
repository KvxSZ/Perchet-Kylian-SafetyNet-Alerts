package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.MedicalrecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalrecordService medicalRecordService;
    private Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @Autowired
    public MedicalRecordController(MedicalrecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getMedicalRecord();
    }

    @PostMapping
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        String result = medicalRecordService.addMedicalRecord(medicalRecord);
        if (result.contains("successfully")) {
            logger.info(result);
            return ResponseEntity.ok(result);
        } else {
            logger.error("Failed to add MedicalRecord");
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> updateMedicalRecord(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestBody MedicalRecord updatedMedicalRecord) {
        String result = medicalRecordService.updateMedicalRecord(firstName, lastName, updatedMedicalRecord);
        if (result.contains("successfully")) {
            logger.info(result);
            return ResponseEntity.ok(result);
        } else {
            logger.error(result);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        String result = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (result.contains("successfully")) {
            logger.info(result);
            return ResponseEntity.ok(result);
        } else {
            logger.error(result);
            return ResponseEntity.notFound().build();
        }
    }
}