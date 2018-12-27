package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class FlightsToVacationsTable {

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
}
