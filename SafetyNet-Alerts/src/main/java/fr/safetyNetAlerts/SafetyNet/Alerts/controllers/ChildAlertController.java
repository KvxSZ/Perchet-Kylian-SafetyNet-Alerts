package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO.ChildAndOtherResident;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.MedicalrecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/childAlert")
public class ChildAlertController {

    MedicalrecordService medicalrecordService;
    private Logger logger = LoggerFactory.getLogger(ChildAlertController.class);

    @Autowired
    public ChildAlertController(MedicalrecordService medicalrecordService) {
        this.medicalrecordService = medicalrecordService;
    }

    @GetMapping
    public ResponseEntity<ChildAndOtherResident> getChildAlert(@RequestParam("address") String address) {
        if (medicalrecordService.getChildAndOtherResident(address) != null) {
            logger.info("Data successfully recovered");
            return ResponseEntity.ok(medicalrecordService.getChildAndOtherResident(address));
        } else {
            logger.error("Failed data recovery");
            return ResponseEntity.notFound().build();
        }

    }


}
