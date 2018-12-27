package Model.Objects;

import java.time.LocalDate;

public class Flight {

    private String flightCompany;
    private String sourceAirPort;
    private String destinationAirPort;
    private LocalDate departDate;
    private LocalDate landDate;
    private String departHour;
    private String landHour;

    public Flight(String flightCompany, String sourceAirPort, String destinationAirPort, LocalDate departDate, LocalDate landDate, String departHour, String landHour) {
        this.flightCompany = flightCompany;
        this.sourceAirPort = sourceAirPort;
        this.destinationAirPort = destinationAirPort;
        this.departDate = departDate;
        this.landDate = landDate;
        this.departHour = departHour;
        this.landHour = landHour;
    }

    public String getFlightCompany() {
        return flightCompany;
    }

    public String getSourceAirPort() {
        return sourceAirPort;
    }

    public String getDestinationAirPort() {
        return destinationAirPort;
    }

    public LocalDate getDepartDate() {
        return departDate;
    }

    public LocalDate getLandDate() {
        return landDate;
    }

    public String getDepartHour() {
        return departHour;
    }

    public String getLandHour() {
        return landHour;
    }

    public int compare(Flight flight){
        if(this.departDate.compareTo(flight.departDate)<0)
            return -1;
        else if(this.departDate.compareTo(flight.departDate)>0)
            return 1;
        return this.departHour.compareTo(flight.departHour);
    }
}


