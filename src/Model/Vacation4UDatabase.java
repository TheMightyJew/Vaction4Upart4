package Model;

import Model.Objects.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Vacation4UDatabase {
    private FlightsTable flightsTable;
    private FlightsToVacationsTable flightsToVacationsTable;
    private PaymentsTable paymentsTable;
    private PurchaseRequestsTable purchaseRequestsTable;
    private UsersTable usersTable;
    private VacationsTable vacationsTable;

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
        return usersTable.UsersTable_checkPassword(Username_val, Password_val);
    }

    public boolean userExist(String username){
        return usersTable.userExist(username);
    }

    public boolean deleteUser(String username){return usersTable.deleteUser(username);}

    public User getUser(String username_val){return usersTable.getUser(username_val);}

    public boolean publishVacation(Vacation vacation) {
        return vacationsTable.publishVacation(vacation);
    }

    public List<VacationSell> getVacations(String username, String flightCompany, LocalDate fromDate, LocalDate toDate, boolean baggage, Integer baggageMin, Integer ticketsNum, Vacation.Tickets_Type tickets_type, Integer maxPricePerTicket, String sourceCountry, String destCountry, Vacation.Vacation_Type vacation_type, Vacation.Flight_Type flight_type, boolean hospitalityIncluded, Integer minHospitalityRank) {
        return vacationsTable.getVacations(username, flightCompany, fromDate, toDate, baggage, baggageMin, ticketsNum, tickets_type, maxPricePerTicket, sourceCountry, destCountry, vacation_type, flight_type, hospitalityIncluded, minHospitalityRank);
    }

    public boolean sendRequest(Request request) {
        return purchaseRequestsTable.sendRequest(request);
    }

    public List<PurchaseRequest> getMyRequests(String username) {
        return purchaseRequestsTable.getMyRequests(username);
    }

    public List<PurchaseRequest> getReceivedRequests(String username) {
        return purchaseRequestsTable.getReceivedRequests(username);
    }

    public boolean acceptRequest(int requestId) {
        return purchaseRequestsTable.acceptRequest(requestId);
    }

    public boolean rejectRequest(int requestId) {
        return purchaseRequestsTable.rejectRequest(requestId);
    }
}