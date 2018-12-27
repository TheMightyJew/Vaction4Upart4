package Model;

import Model.Objects.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseRequestsTable extends AVacationdatabaseTable {

    public enum PurchaseRequestsfieldNameEnum {PurchaseRequest_id, Requester_Username, Vacation_id, Request_Status;}

    public PurchaseRequestsTable(String databaseName) {
        // SQLite connection string
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS PurchaseRequests_Table (\n"
                    + "PurchaseRequest_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "Requester_Username text NOT NULL,\n"
                    + "Vacation_id text NOT NULL,\n"
                    + "Request_Status text NOT NULL\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean sendRequest(Request request) {
        try {
            String[] values = {null, request.getUsername(), "" + request.getVacationId(), PurchaseRequest.Request_Status.pending.name()};
            insertQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.class, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<PurchaseRequest> getMyRequests(String username) {
        List<String[]> results = selectQuery(tableNameEnum.PurchaseRequests_Table.name(), PurchaseRequestsfieldNameEnum.Requester_Username + "='" + username + "'");
        List<PurchaseRequest> ans = new ArrayList<>();
        for (String[] row : results) {
            List<String[]> vaction_of_request = selectQuery(tableNameEnum.Vacations_Table.name(), VacationsTable.VacationsfieldNameEnum.Vacation_id + "='" + row[2] + "'");//get information to create vacation
            if (vaction_of_request.size() != 1)
                return null;
            String[] vacation = vaction_of_request.get(0);
            List<Flight> flightForCreateVacation = new ArrayList<>();
            List<String[]> flight_of_vacation = selectQuery(tableNameEnum.FlightsToVacations_Table.name(), FlightsToVacationsTable.FlightsToVacationsfieldNameEnum.Vacation_id + "='" + row[2] + "'");//need
            for (String[] flight : flight_of_vacation) {
                List<String[]> flightInfo = selectQuery(tableNameEnum.Flights_table.name(), FlightsTable.FlightsfieldNameEnum.FlightID + "='" + flight[1] + "'");
                for (String[] fly : flightInfo) {
                    Flight flightToList = new Flight(fly[7], fly[1], fly[2], LocalDate.parse(fly[3]), LocalDate.parse(fly[5]), fly[4], fly[6]);
                    flightForCreateVacation.add(flightToList);
                }
            }
            Vacation vac = new Vacation(vacation[1], LocalDate.parse(vacation[4]), LocalDate.parse(vacation[5]), Integer.parseInt(vacation[6]), Integer.parseInt(vacation[7]), vacation[8].equals("true"), vacation[2], vacation[3], (Integer.parseInt(vacation[12]) > 0), Integer.parseInt(vacation[12]), Vacation.Tickets_Type.valueOf(vacation[9]), flightForCreateVacation, Vacation.Flight_Type.valueOf(vacation[11]), Vacation.Vacation_Type.valueOf(vacation[10]), vacation[13].equals("true"), Integer.parseInt(vacation[14]));
            VacationSell vacSell = new VacationSell(Integer.parseInt(vacation[0]), vac, VacationSell.Vacation_Status.valueOf(vacation[15]));
            PurchaseRequest purchaseRequest = new PurchaseRequest(Integer.parseInt(row[0]), row[1], vacSell, PurchaseRequest.Request_Status.valueOf(row[3]));
            ans.add(purchaseRequest);
        }
        return ans;
    }

    public List<PurchaseRequest> getReceivedRequests(String username) {
        List<PurchaseRequest> ans = new ArrayList<>();
        List<String[]> ansfromSqlSelect = new ArrayList<>();
        String sql = "select PurchaseRequests_Table.*\n" +
                "from Vacations_Table,PurchaseRequests_Table\n" +
                "where Vacations_Table.Vacation_id = PurchaseRequests_Table.Vacation_id AND Vacations_Table.Publisher_Username='" + username + "'AND PurchaseRequests_Table.Request_Status='pending';";
        ansfromSqlSelect = specificSelectQuery(sql);
        if (ansfromSqlSelect == null)
            return null;
        for (String[] record : ansfromSqlSelect) {
            String[] temp_vec = selectQuery(tableNameEnum.Vacations_Table.toString(), VacationsTable.VacationsfieldNameEnum.Vacation_id.toString() + "='" + record[2] + "'").get(0);

            List<Flight> flightForCreateVacation = new ArrayList<>();
            List<String[]> flight_of_vacation = selectQuery(tableNameEnum.FlightsToVacations_Table.name(), FlightsToVacationsTable.FlightsToVacationsfieldNameEnum.Vacation_id + "='" + temp_vec[0] + "'");
            for (String[] flight : flight_of_vacation) {
                List<String[]> flightInfo = selectQuery(tableNameEnum.Flights_table.name(), FlightsTable.FlightsfieldNameEnum.FlightID + "='" + flight[1] + "'");
                for (String[] fly : flightInfo) {
                    Flight flightToList = new Flight(fly[7], fly[1], fly[2], LocalDate.parse(fly[3]), LocalDate.parse(fly[5]), fly[4], fly[6]);
                    flightForCreateVacation.add(flightToList);

                }
            }
            Vacation vac = new Vacation(temp_vec[1], LocalDate.parse(temp_vec[4]), LocalDate.parse(temp_vec[5]), Integer.parseInt(temp_vec[6]), Integer.parseInt(temp_vec[7]), temp_vec[8].equals("true"), temp_vec[2], temp_vec[3], (Integer.parseInt(temp_vec[12]) > 0), Integer.parseInt(temp_vec[12]), Vacation.Tickets_Type.valueOf(temp_vec[9]), flightForCreateVacation, Vacation.Flight_Type.valueOf(temp_vec[11]), Vacation.Vacation_Type.valueOf(temp_vec[10]), temp_vec[13].equals("true"), Integer.parseInt(temp_vec[14]));
            VacationSell vacSell = new VacationSell(Integer.parseInt(temp_vec[0]), vac, VacationSell.Vacation_Status.valueOf(temp_vec[15]));
            PurchaseRequest psrq = new PurchaseRequest(Integer.parseInt(record[0]), record[1], vacSell, PurchaseRequest.Request_Status.valueOf(record[3]));
            ans.add(psrq);
        }
        return ans;
    }

    public boolean acceptRequest(int requestId) {
        String[] values = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.PurchaseRequest_id + "='" + requestId + "'").get(0);
        values[3] = PurchaseRequest.Request_Status.accepted.toString();
        try {
            updateQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.class, values, PurchaseRequestsfieldNameEnum.PurchaseRequest_id + "='" + requestId + "'");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean rejectRequest(int requestId) {
        String[] values = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.PurchaseRequest_id + "='" + requestId + "'").get(0);
        values[3] = PurchaseRequest.Request_Status.rejected.toString();
        try {
            updateQuery(tableNameEnum.PurchaseRequests_Table.toString(),PurchaseRequestsfieldNameEnum.class, values, PurchaseRequestsfieldNameEnum.PurchaseRequest_id + "='" + requestId + "'");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
