package fr.safetyNetAlerts.SafetyNet.Alerts.controllers;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.FireStation;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.FirestationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationService fireStationService;

    @Autowired
    public FirestationController(FirestationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFireStation(@RequestBody FireStation fireStation) {
        String result = fireStationService.addFireStation(fireStation);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{address}")
    public ResponseEntity<String> updateFireStation(
            @PathVariable String address,
            @RequestBody FireStation updatedFireStation) {
        String result = fireStationService.updateFireStation(address, updatedFireStation);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{address}")
    public ResponseEntity<String> deleteFireStation(@PathVariable String address) {
        String result = fireStationService.deleteFireStation(address);
        return ResponseEntity.ok(result);
    }
}