package fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO;

import java.util.List;

public record ResidentDetailsDAO(String name, String phone, int age, List<String> medications, List<String> allergies) {
}
