package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class FlightsTable {

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
