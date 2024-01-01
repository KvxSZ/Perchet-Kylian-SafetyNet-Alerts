package fr.safetyNetAlerts.SafetyNet.Alerts.service;

import fr.safetyNetAlerts.SafetyNet.Alerts.model.FireStation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {

    private final EmergencyService emergencyService;

    public FirestationService(EmergencyService emergencyService) {
        this.emergencyService = emergencyService;
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
}
