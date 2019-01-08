package Model.Database;

import Model.Objects.Vacation;
import Model.Requests.TradeARequest;
import Model.Requests.TradeRequestData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TradeRequestsTable extends AVacationdatabaseTable {
    public enum TradeRequestsfieldNameEnum {TradeRequest_id, Offered_Vacation_id, Wanted_Vacation_id, Request_Status;}

    public TradeRequestsTable(String databaseName) {
        // SQLite connection string
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS TradeRequests_Table (\n"
                    + "TradeRequest_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "Offered_Vacation_id text NOT NULL,\n"
                    + "Wanted_Vacation_id text NOT NULL,\n"
                    + "Request_Status text NOT NULL\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean sendRequest(TradeRequestData request) {
        List<String[]> valuesExists = selectQuery(tableNameEnum.TradeRequests_Table.name(), TradeRequestsfieldNameEnum.Offered_Vacation_id.name() + "='" + request.getOfferedVacation() + "' AND " + TradeRequestsfieldNameEnum.Wanted_Vacation_id.name() + "='" + request.getWantedVacation() + "'");
        List<String[]> valuesExistsOpposite = selectQuery(tableNameEnum.TradeRequests_Table.name(), TradeRequestsfieldNameEnum.Offered_Vacation_id.name() + "='" + request.getWantedVacation() + "' AND " + TradeRequestsfieldNameEnum.Wanted_Vacation_id.name() + "='" + request.getOfferedVacation() + "'");
        if(valuesExists.size()>0 || valuesExistsOpposite.size()>0)
            return false;
        try {
            String[] values = {null, "" + request.getOfferedVacation(), "" + request.getWantedVacation(), TradeARequest.Request_Status.pending.name()};
            insertQuery(tableNameEnum.TradeRequests_Table.toString(), TradeRequestsTable.TradeRequestsfieldNameEnum.class, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String[]> getRequestByOfferdVacationID(String offeredVacationID) {
        return selectQuery(tableNameEnum.TradeRequests_Table.name(), TradeRequestsfieldNameEnum.Offered_Vacation_id + "='" + offeredVacationID + "'");
    }

    public List<String[]> getRequestByWantedVacationID(String wantedvacationID) {
        return selectQuery(tableNameEnum.TradeRequests_Table.name(), TradeRequestsfieldNameEnum.Wanted_Vacation_id + "='" + wantedvacationID + "'");

    }

//    public List<TradeARequest> getMyRequests(String username) {
//        //TODO: 02-Jan-19
//        List<TradeARequest> ans = new ArrayList<>();
//        List<String[]> myVacations = selectQuery(tableNameEnum.Vacations_Table.name(), VacationsTable.VacationsfieldNameEnum.Publisher_Username + "='" + username + "'");
//        List<String[]> myTradeRequest = new ArrayList<>();
//        for (String[] myVacation : myVacations) {
//            List<String[]> requestByVacation = selectQuery(tableNameEnum.TradeRequests_Table.name(), TradeRequestsfieldNameEnum.Offered_Vacation_id + "='" + myVacation[0] + "'");
//            Vacation offeredvacation = new Vacation(myVacation[1], LocalDate.parse(myVacation[4]), LocalDate.parse(myVacation[5]), Integer.parseInt(myVacation[6]), Integer.parseInt(myVacation[7]), myVacation[8].equals("true"), myVacation[2], myVacation[3], (Integer.parseInt(myVacation[12]) > 0), Integer.parseInt(myVacation[12]), Vacation.Tickets_Type.valueOf(myVacation[9]), flightForCreateVacation, Vacation.Flight_Type.valueOf(vacation[11]), Vacation.Vacation_Type.valueOf(vacation[10]), vacation[13].equals("true"), Integer.parseInt(vacation[14]))
//        }
//
//        List<String[]> results = selectQuery(tableNameEnum.PurchaseRequests_Table.name(), PurchaseRequestsTable.PurchaseRequestsfieldNameEnum.Requester_Username + "='" + username + "'");
//        List<PurchaseARequest> ans = new ArrayList<>();
//        for (String[] row : results) {
//            List<String[]> vaction_of_request = selectQuery(tableNameEnum.Vacations_Table.name(), VacationsTable.VacationsfieldNameEnum.Vacation_id + "='" + row[2] + "'");//get information to create vacation
//            if (vaction_of_request.size() != 1)
//                return null;
//            String[] vacation = vaction_of_request.get(0);
//            List<Flight> flightForCreateVacation = new ArrayList<>();
//            List<String[]> flight_of_vacation = selectQuery(tableNameEnum.FlightsToVacations_Table.name(), FlightsToVacationsTable.FlightsToVacationsfieldNameEnum.Vacation_id + "='" + row[2] + "'");//need
//            for (String[] flight : flight_of_vacation) {
//                List<String[]> flightInfo = selectQuery(tableNameEnum.Flights_table.name(), FlightsTable.FlightsfieldNameEnum.FlightID + "='" + flight[1] + "'");
//                for (String[] fly : flightInfo) {
//                    Flight flightToList = new Flight(fly[7], fly[1], fly[2], LocalDate.parse(fly[3]), LocalDate.parse(fly[5]), fly[4], fly[6]);
//                    flightForCreateVacation.add(flightToList);
//                }
//            }
//            Vacation vac = new Vacation(vacation[1], LocalDate.parse(vacation[4]), LocalDate.parse(vacation[5]), Integer.parseInt(vacation[6]), Integer.parseInt(vacation[7]), vacation[8].equals("true"), vacation[2], vacation[3], (Integer.parseInt(vacation[12]) > 0), Integer.parseInt(vacation[12]), Vacation.Tickets_Type.valueOf(vacation[9]), flightForCreateVacation, Vacation.Flight_Type.valueOf(vacation[11]), Vacation.Vacation_Type.valueOf(vacation[10]), vacation[13].equals("true"), Integer.parseInt(vacation[14]));
//            VacationSell vacSell = new VacationSell(Integer.parseInt(vacation[0]), vac, VacationSell.Vacation_Status.valueOf(vacation[15]));
//            PurchaseARequest purchaseRequest = new PurchaseARequest(Integer.parseInt(row[0]), row[1], vacSell, PurchaseARequest.Request_Status.valueOf(row[3]));
//            ans.add(purchaseRequest);
//        }
//        return ans;
//        return null;
//    }

    public List<TradeARequest> getReceivedRequests(String username) {
        // TODO: 02-Jan-19
//        List<PurchaseARequest> ans = new ArrayList<>();
//        List<String[]> ansfromSqlSelect = new ArrayList<>();
//        String sql = "select PurchaseRequests_Table.*\n" +
//                "from Vacations_Table,PurchaseRequests_Table\n" +
//                "where Vacations_Table.Vacation_id = PurchaseRequests_Table.Vacation_id AND Vacations_Table.Publisher_Username='" + username + "'AND PurchaseRequests_Table.Request_Status='pending';";
//        ansfromSqlSelect = specificSelectQuery(sql);
//        if (ansfromSqlSelect == null)
//            return null;
//        for (String[] record : ansfromSqlSelect) {
//            String[] temp_vec = selectQuery(tableNameEnum.Vacations_Table.toString(), VacationsTable.VacationsfieldNameEnum.Vacation_id.toString() + "='" + record[2] + "'").get(0);
//
//            List<Flight> flightForCreateVacation = new ArrayList<>();
//            List<String[]> flight_of_vacation = selectQuery(tableNameEnum.FlightsToVacations_Table.name(), FlightsToVacationsTable.FlightsToVacationsfieldNameEnum.Vacation_id + "='" + temp_vec[0] + "'");
//            for (String[] flight : flight_of_vacation) {
//                List<String[]> flightInfo = selectQuery(tableNameEnum.Flights_table.name(), FlightsTable.FlightsfieldNameEnum.FlightID + "='" + flight[1] + "'");
//                for (String[] fly : flightInfo) {
//                    Flight flightToList = new Flight(fly[7], fly[1], fly[2], LocalDate.parse(fly[3]), LocalDate.parse(fly[5]), fly[4], fly[6]);
//                    flightForCreateVacation.add(flightToList);
//
//                }
//            }
//            Vacation vac = new Vacation(temp_vec[1], LocalDate.parse(temp_vec[4]), LocalDate.parse(temp_vec[5]), Integer.parseInt(temp_vec[6]), Integer.parseInt(temp_vec[7]), temp_vec[8].equals("true"), temp_vec[2], temp_vec[3], (Integer.parseInt(temp_vec[12]) > 0), Integer.parseInt(temp_vec[12]), Vacation.Tickets_Type.valueOf(temp_vec[9]), flightForCreateVacation, Vacation.Flight_Type.valueOf(temp_vec[11]), Vacation.Vacation_Type.valueOf(temp_vec[10]), temp_vec[13].equals("true"), Integer.parseInt(temp_vec[14]));
//            VacationSell vacSell = new VacationSell(Integer.parseInt(temp_vec[0]), vac, VacationSell.Vacation_Status.valueOf(temp_vec[15]));
//            PurchaseARequest psrq = new PurchaseARequest(Integer.parseInt(record[0]), record[1], vacSell, PurchaseARequest.Request_Status.valueOf(record[3]));
//            ans.add(psrq);
//        }
//        return ans;
        return null;

    }

    public boolean acceptRequest(int requestId) {
        String[] values = selectQuery(tableNameEnum.TradeRequests_Table.toString(), TradeRequestsfieldNameEnum.TradeRequest_id + "='" + requestId + "'").get(0);
        values[3] = TradeARequest.Request_Status.accepted.toString();
        try {
            updateQuery(tableNameEnum.TradeRequests_Table.toString(), TradeRequestsfieldNameEnum.class, values, TradeRequestsfieldNameEnum.TradeRequest_id + "='" + requestId + "'");
            // TODO: 08/01/2019 decline all other requests
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public String[] getVacationsIDs(int requestID) {
        String[] values = selectQuery(tableNameEnum.TradeRequests_Table.toString(), TradeRequestsfieldNameEnum.TradeRequest_id + "='" + requestID + "'").get(0);
        String[] IDs = new String[]{values[1], values[2]};
        return IDs;
    }

    public boolean rejectRequest(int requestId) {
        String[] values = selectQuery(tableNameEnum.TradeRequests_Table.toString(), TradeRequestsfieldNameEnum.TradeRequest_id + "='" + requestId + "'").get(0);
        values[3] = TradeARequest.Request_Status.rejected.toString();
        try {
            updateQuery(tableNameEnum.TradeRequests_Table.toString(), TradeRequestsfieldNameEnum.class, values, TradeRequestsfieldNameEnum.TradeRequest_id + "='" + requestId + "'");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
