package fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO;

import java.util.List;

public record PersonCoveredByStationDTO(int children, int adults, List<PersonDTO> people) {
}
