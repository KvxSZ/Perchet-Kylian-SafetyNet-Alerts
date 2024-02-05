package fr.safetyNetAlerts.SafetyNet.Alerts.controllers.DTO;

import java.util.List;

public record ChildAndOtherResident(List<ResidentInfoDAO> childrenInfo, List<ResidentInfoDAO> otherResidentsMenbers) {
}
