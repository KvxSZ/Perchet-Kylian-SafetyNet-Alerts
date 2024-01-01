package fr.safetyNetAlerts.SafetyNet.Alerts.model;

public class FireStation {

    private String address;
    private String station;

    public FireStation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    public FireStation(){

    }

    @Override
    public String toString() {
        return "FireStation{" +
                "address='" + address + '\'' +
                ", station='" + station + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public String getStation() {
        return station;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
