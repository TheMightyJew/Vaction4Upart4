package Model.Database;

import Model.Objects.*;
import Model.Requests.PurchaseARequest;
import Model.Requests.PurchaseRequestData;

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

    public boolean sendRequest(PurchaseRequestData purchaseRequestData) {
        List<String[]> valuesExists = selectQuery(tableNameEnum.PurchaseRequests_Table.name(), PurchaseRequestsfieldNameEnum.Vacation_id.name() + "='" + purchaseRequestData.getVacationId() + "' AND "+ PurchaseRequestsfieldNameEnum.Requester_Username.name()+"='"+purchaseRequestData.getUsername()+"'");
        if(valuesExists.size()>0)
            return false;
        try {
            String[] values = {null, purchaseRequestData.getUsername(), "" + purchaseRequestData.getVacationId(), PurchaseARequest.Request_Status.pending.name()};
            insertQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.class, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public List<String[]> getMyRequestsSting(String username) {
        List<String[]> results = selectQuery(tableNameEnum.PurchaseRequests_Table.name(), PurchaseRequestsfieldNameEnum.Requester_Username + "='" + username + "'");
        return results;
    }

    public List<PurchaseARequest> getReceivedRequests(String username) {
        List<PurchaseARequest> ans = new ArrayList<>();
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
            PurchaseARequest psrq = new PurchaseARequest(Integer.parseInt(record[0]), record[1], vacSell, PurchaseARequest.Request_Status.valueOf(record[3]));
            ans.add(psrq);
        }
        return ans;
    }

    public boolean acceptRequest(int requestId) {
        String[] values = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.PurchaseRequest_id + "='" + requestId + "'").get(0);
        values[3] = PurchaseARequest.Request_Status.accepted.toString();
        try {
            updateQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.class, values, PurchaseRequestsfieldNameEnum.PurchaseRequest_id + "='" + requestId + "'");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public String[] getRequestDetail(int requestID) {
        String[] values = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.PurchaseRequest_id + "='" + requestID + "'").get(0);
        return values;
    }


    public List<String[]> getRequestForVacations(String vacationID)
    {
        List<String[]> toReject = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.Vacation_id + "='" + vacationID + "'");
        return toReject;
    }

    public boolean rejectRequest(int requestId) {
        String[] values = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.PurchaseRequest_id + "='" + requestId + "'").get(0);
        values[3] = PurchaseARequest.Request_Status.rejected.toString();
        try {
            updateQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.class, values, PurchaseRequestsfieldNameEnum.PurchaseRequest_id + "='" + requestId + "'");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public String getVacationID(int requestID) {
        String[] values = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.Vacation_id + "='" + requestID + "'").get(0);
        String ID = values[2];
        return ID;
    }
}
