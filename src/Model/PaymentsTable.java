package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class PaymentsTable {

    public enum PaymentfieldsNameEnum {PaymentID, Username, VacationID, Payment_Method;}

    public PaymentsTable(String databaseName) {
        // SQLite connection string
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Payments_Table (\n"
                    + "PaymentID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "Username text NOT NULL,\n"
                    + "VacationID text NOT NULL,\n"
                    + "Payment_Method text NOT NULL\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
