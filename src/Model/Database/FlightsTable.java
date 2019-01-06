package Model.Database;

import Model.Model;
import Model.Objects.Flight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

public class FlightsTable extends AVacationdatabaseTable {


    public List<String[]> getFlightInfoString(String s) {
        List<String[]> flightInfo = selectQuery(AVacationdatabaseTable.tableNameEnum.Flights_table.name(), FlightsTable.FlightsfieldNameEnum.FlightID + "='" + s + "'");
        return flightInfo;
    }

    public boolean insertFlight(Flight flight) {
        return insertQuery(tableNameEnum.Flights_table.name(), FlightsTable.FlightsfieldNameEnum.class, new String[]{null, flight.getSourceAirPort(), flight.getDestinationAirPort(), flight.getDepartDate().toString(), flight.getDepartHour(), flight.getLandDate().toString(), flight.getLandHour(), flight.getFlightCompany()});
    }

    public String getFlightId(Flight flight) {
        String flightID = selectQuery(Model.tableNameEnum.Flights_table.name(), FlightsTable.FlightsfieldNameEnum.OriginAirport.name() + "='" + flight.getSourceAirPort() + "' AND " + FlightsTable.FlightsfieldNameEnum.DestinationAirport.name() + "='" + flight.getDestinationAirPort() + "' AND " + FlightsTable.FlightsfieldNameEnum.DepartureDate.name() + "='" + flight.getDepartDate().toString() + "' AND "
                + FlightsTable.FlightsfieldNameEnum.DepartureTime.name() + "='" + flight.getDepartHour() + "' AND " + FlightsTable.FlightsfieldNameEnum.ArrivalDate.name() + "='" + flight.getLandDate().toString() + "' AND " + FlightsTable.FlightsfieldNameEnum.ArrivalTime.name() + "='" + flight.getLandHour() + "' AND " + FlightsTable.FlightsfieldNameEnum.FlightComapny.name() + "='" + flight.getFlightCompany() + "'").get(0)[0];
        return flightID;
    }

    public enum FlightsfieldNameEnum {FlightID, OriginAirport, DestinationAirport, DepartureDate, DepartureTime, ArrivalDate, ArrivalTime, FlightComapny;}

    public FlightsTable(String databaseName) {
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Flights_Table (\n"
                    + "FlightID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "OriginAirport text NOT NULL,\n"
                    + "DestinationAirport text NOT NULL,\n"
                    + "DepartureDate text,\n"
                    + "DepartureTime text,\n"
                    + "ArrivalDate text,\n"
                    + "ArrivalTime text,\n"
                    + "FlightComapny text\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
