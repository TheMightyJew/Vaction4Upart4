package Model.Database;

import Model.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

public class FlightsToVacationsTable extends AVacationdatabaseTable {

    public boolean insertFlightToVacation(String flightID, String vacationID) {
        return insertQuery(Model.Model.tableNameEnum.FlightsToVacations_Table.name(), FlightsToVacationsTable.FlightsToVacationsfieldNameEnum.class, new String[]{String.valueOf(vacationID), String.valueOf(flightID)});
    }

    public enum FlightsToVacationsfieldNameEnum {Vacation_id, Flight_id}

    public FlightsToVacationsTable(String databaseName) {
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS FlightsToVacations_Table (\n"
                    + "Vacation_id text NOT NULL,\n"
                    + "Flight_id text NOT NULL,\n"
                    + "	CONSTRAINT PK_FTV PRIMARY KEY (Vacation_id,Flight_id)\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getFlightsOfVacationsString(String vacationId) {
        List<String[]> flight_of_vacation = selectQuery(AVacationdatabaseTable.tableNameEnum.FlightsToVacations_Table.name(), FlightsToVacationsTable.FlightsToVacationsfieldNameEnum.Vacation_id + "='" + vacationId + "'");//need
        return flight_of_vacation;
    }
}
