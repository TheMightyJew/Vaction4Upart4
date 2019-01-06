package Model.Database;

import Model.Model;
import Model.Objects.*;
import Model.Requests.PurchaseARequest;
import Model.Requests.PurchaseRequestData;
import Model.Requests.TradeARequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vacation4UDatabase {
    private FlightsTable flightsTable;
    private FlightsToVacationsTable flightsToVacationsTable;
    private PaymentsTable paymentsTable;
    private PurchaseRequestsTable purchaseRequestsTable;
    private UsersTable usersTable;
    private VacationsTable vacationsTable;
    private TradeRequestsTable tradeRequestsTable;

    String databaseName = "EveryVaction4U.db";

    public Vacation4UDatabase() {
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + "EveryVaction4U.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        flightsTable = new FlightsTable(databaseName);
        flightsToVacationsTable = new FlightsToVacationsTable(databaseName);
        paymentsTable = new PaymentsTable(databaseName);
        purchaseRequestsTable = new PurchaseRequestsTable(databaseName);
        usersTable = new UsersTable(databaseName);
        vacationsTable = new VacationsTable(databaseName);
    }

    public boolean createUser(User user) {
        return usersTable.createUser(user);
    }

    public boolean updateUserInfo(String username, User user) {
        return usersTable.updateUserInfo(username, user);
    }

    public boolean UsersTable_checkPassword(String Username_val, String Password_val) {
        return usersTable.checkPassword(Username_val, Password_val);
    }

    public boolean userExist(String username){
        return usersTable.userExist(username);
    }

    public boolean deleteUser(String username){return usersTable.deleteUser(username);}

    public User getUser(String username_val){return usersTable.getUser(username_val);}

    public boolean publishVacation(Vacation vacation) {

        int baggage = vacation.isBaggage_Included() ? 1 : 0;
        if (baggage > 0)
            baggage = vacation.getBaggageLimit();
        if(vacationsTable.insertVcation(vacation,baggage)==false)
            return false;
        String vacationID = vacationsTable.getVacationID(vacation,baggage);
        for (Flight flight : vacation.getFlights()) {
            if(flightsTable.insertFlight(flight)==false)
                return false;
            String flightID = flightsTable.getFlightId(flight);
            if(flightsToVacationsTable.insertFlightToVacation(flightID,vacationID)==false)
                return false;
        }
        return true;
    }

    public List<VacationSell> getVacations(String username, String flightCompany, LocalDate fromDate, LocalDate toDate, boolean baggage, Integer baggageMin, Integer ticketsNum, Vacation.Tickets_Type tickets_type, Integer maxPricePerTicket, String sourceCountry, String destCountry, Vacation.Vacation_Type vacation_type, Vacation.Flight_Type flight_type, boolean hospitalityIncluded, Integer minHospitalityRank) {
        return vacationsTable.getVacations(username, flightCompany, fromDate, toDate, baggage, baggageMin, ticketsNum, tickets_type, maxPricePerTicket, sourceCountry, destCountry, vacation_type, flight_type, hospitalityIncluded, minHospitalityRank);
    }

    public boolean sendRequest(PurchaseRequestData purchaseRequestData) {
        return purchaseRequestsTable.sendRequest(purchaseRequestData);
    }

    public List<PurchaseARequest> getMyPurchaseRequests(String username) {
        List<String[]> results = purchaseRequestsTable.getMyRequestsSting(username);
        List<PurchaseARequest> ans = new ArrayList<>();
        for (String[] row : results) {
            String[] vacation = vacationsTable.getVacationsString(row[2]);
            List<Flight> flightForCreateVacation = new ArrayList<>();
            List<String[]> flight_of_vacation = flightsToVacationsTable.getFlightsOfVacationsString(row[2]);
            for (String[] flight : flight_of_vacation) {
                List<String[]> flightInfo = flightsTable.getFlightInfoString(flight[1]);
                for (String[] fly : flightInfo) {
                    Flight flightToList = new Flight(fly[7], fly[1], fly[2], LocalDate.parse(fly[3]), LocalDate.parse(fly[5]), fly[4], fly[6]);
                    flightForCreateVacation.add(flightToList);
                }
            }
            Vacation vac = new Vacation(vacation[1], LocalDate.parse(vacation[4]), LocalDate.parse(vacation[5]), Integer.parseInt(vacation[6]), Integer.parseInt(vacation[7]), vacation[8].equals("true"), vacation[2], vacation[3], (Integer.parseInt(vacation[12]) > 0), Integer.parseInt(vacation[12]), Vacation.Tickets_Type.valueOf(vacation[9]), flightForCreateVacation, Vacation.Flight_Type.valueOf(vacation[11]), Vacation.Vacation_Type.valueOf(vacation[10]), vacation[13].equals("true"), Integer.parseInt(vacation[14]));
            VacationSell vacSell = new VacationSell(Integer.parseInt(vacation[0]), vac, VacationSell.Vacation_Status.valueOf(vacation[15]));
            PurchaseARequest purchaseRequest = new PurchaseARequest(Integer.parseInt(row[0]), row[1], vacSell, PurchaseARequest.Request_Status.valueOf(row[3]));
            ans.add(purchaseRequest);
        }
        return ans;
    }

    public List<PurchaseARequest> getReceivedPurchaseRequests(String username) {
        return purchaseRequestsTable.getReceivedRequests(username);
    }

    public List<TradeARequest> getMyTradeRequests(String username) {
        return tradeRequestsTable.getMyRequests(username);
    }

    public List<TradeARequest> getReceivedTradeRequests(String username) {
        return tradeRequestsTable.getReceivedRequests(username);
    }

    public boolean acceptPurchaseRequest(int requestId) {
        return purchaseRequestsTable.acceptRequest(requestId);
    }

    public boolean rejectPurchaseRequest(int requestId) {
        return purchaseRequestsTable.rejectRequest(requestId);
    }

    public boolean acceptTradeRequest(int requestId) {
        return tradeRequestsTable.acceptRequest(requestId);
    }

    public boolean rejectTradeRequest(int requestId) {
        return tradeRequestsTable.rejectRequest(requestId);
    }
}
