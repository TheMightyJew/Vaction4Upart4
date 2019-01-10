package Model.Database;

import Model.Model;
import Model.Objects.*;
import Model.Requests.ARequest;
import Model.Requests.PurchaseARequest;
import Model.Requests.PurchaseRequestData;
import Model.Requests.TradeARequest;
import Model.Requests.TradeRequestData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vacation4UDatabase {
    private FlightsTable flightsTable;
    private FlightsToVacationsTable flightsToVacationsTable;
    //    private PaymentsTable paymentsTable;
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
//        paymentsTable = new PaymentsTable(databaseName);
        purchaseRequestsTable = new PurchaseRequestsTable(databaseName);
        usersTable = new UsersTable(databaseName);
        vacationsTable = new VacationsTable(databaseName);
        tradeRequestsTable = new TradeRequestsTable(databaseName);
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

    public boolean userExist(String username) {
        return usersTable.userExist(username);
    }

    public boolean deleteUser(String username) {
        return usersTable.deleteUser(username);
    }

    public User getUser(String username_val) {
        return usersTable.getUser(username_val);
    }

    public boolean publishVacation(Vacation vacation) {

        int baggage = vacation.isBaggage_Included() ? 1 : 0;
        if (baggage > 0)
            baggage = vacation.getBaggageLimit();
        if (vacationsTable.insertVcation(vacation, baggage) == false)
            return false;
        String vacationID = vacationsTable.getVacationID(vacation, baggage);
        for (Flight flight : vacation.getFlights()) {
            if (flightsTable.insertFlight(flight) == false)
                return false;
            String flightID = flightsTable.getFlightId(flight);
            if (flightsToVacationsTable.insertFlightToVacation(flightID, vacationID) == false)
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

    public boolean sendTradeRequest(TradeRequestData tradeRequestData) {
        return tradeRequestsTable.sendRequest(tradeRequestData);
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
        List<TradeARequest> ans = new ArrayList<>();
        List<String[]> userVacations = vacationsTable.getVacationsByUsernameString(username);//get The vacation of user to serach if he want to switched them
        for (String[] userVacation : userVacations) {
            List<String[]> requestByVacation = tradeRequestsTable.getRequestByOfferdVacationID(userVacation[0]);//get the request of the the vacation(as offered)
            if (requestByVacation == null || requestByVacation.size() == 0)
                continue;
            List<String[]> IDflightOfvacation = flightsToVacationsTable.getFlightsOfVacationsString(userVacation[0]);//This is for create the Vacation object of the offered vacation
            List<Flight> FlightsForOfferedVacation = new ArrayList<>();
            for (String[] IDflight : IDflightOfvacation) {
                String[] flight = flightsTable.getFlightByID(IDflight[1]);
                Flight f = new Flight(flight[7], flight[1], flight[2], LocalDate.parse(flight[3]), LocalDate.parse(flight[5]), flight[4], flight[6]);
                FlightsForOfferedVacation.add(f);
            }
            Vacation offeredVacation = new Vacation(userVacation[1], LocalDate.parse(userVacation[4]), LocalDate.parse(userVacation[5]), Integer.parseInt(userVacation[6]), Integer.parseInt(userVacation[7]), userVacation[8].equals("true"), userVacation[2], userVacation[3], (Integer.parseInt(userVacation[12]) > 0), Integer.parseInt(userVacation[12]), Vacation.Tickets_Type.valueOf(userVacation[9]), FlightsForOfferedVacation, Vacation.Flight_Type.valueOf(userVacation[11]), Vacation.Vacation_Type.valueOf(userVacation[10]), userVacation[13].equals("true"), Integer.parseInt(userVacation[14]));
            VacationSell offeredVacationSell = new VacationSell(Integer.parseInt(userVacation[0]), offeredVacation, VacationSell.Vacation_Status.valueOf(userVacation[15]));
            for (String[] request : requestByVacation) {//create the wanted vacation Object
                String wantedVacationID = request[2];
                String[] wantedVacationDetails = vacationsTable.getVacationsString(wantedVacationID);
                IDflightOfvacation = flightsToVacationsTable.getFlightsOfVacationsString(wantedVacationID);
                List<Flight> FlightsForWantedVacation = new ArrayList<>();
                for (String[] IDflight : IDflightOfvacation) {
                    String[] flight = flightsTable.getFlightByID(IDflight[1]);
                    Flight f = new Flight(flight[7], flight[1], flight[2], LocalDate.parse(flight[3]), LocalDate.parse(flight[5]), flight[4], flight[6]);
                    FlightsForWantedVacation.add(f);
                }
                Vacation wantedVacation = new Vacation(wantedVacationDetails[1], LocalDate.parse(wantedVacationDetails[4]), LocalDate.parse(wantedVacationDetails[5]), Integer.parseInt(wantedVacationDetails[6]), Integer.parseInt(wantedVacationDetails[7]), wantedVacationDetails[8].equals("true"), wantedVacationDetails[2], wantedVacationDetails[3], (Integer.parseInt(wantedVacationDetails[12]) > 0), Integer.parseInt(wantedVacationDetails[12]), Vacation.Tickets_Type.valueOf(wantedVacationDetails[9]), FlightsForWantedVacation, Vacation.Flight_Type.valueOf(wantedVacationDetails[11]), Vacation.Vacation_Type.valueOf(wantedVacationDetails[10]), wantedVacationDetails[13].equals("true"), Integer.parseInt(wantedVacationDetails[14]));
                VacationSell wantedVacationSell = new VacationSell(Integer.parseInt(wantedVacationDetails[0]), wantedVacation, VacationSell.Vacation_Status.valueOf(wantedVacationDetails[15]));
                TradeARequest tradeARequest = new TradeARequest(Integer.parseInt(request[0]), ARequest.Request_Status.valueOf(request[3]), wantedVacationSell, offeredVacationSell);
                ans.add(tradeARequest);
            }
        }
        return ans;
    }

    public List<TradeARequest> getReceivedTradeRequests(String username) {
        List<TradeARequest> ans = new ArrayList<>();
        List<String[]> userVacations = vacationsTable.getVacationsByUsernameString(username);//get The vacation of user to serach if he want to switched them
        for (String[] userVacation : userVacations) {
            List<String[]> requestByVacation = tradeRequestsTable.getRequestByWantedVacationID(userVacation[0]);//get the request of the the vacation(as offered)
            if (requestByVacation == null || requestByVacation.size() == 0)
                continue;
            List<String[]> IDflightOfvacation = flightsToVacationsTable.getFlightsOfVacationsString(userVacation[0]);//This is for create the Vacation object of the offered vacation
            List<Flight> FlightsForWantedVacation = new ArrayList<>();
            for (String[] IDflight : IDflightOfvacation) {
                String[] flight = flightsTable.getFlightByID(IDflight[1]);
                Flight f = new Flight(flight[7], flight[1], flight[2], LocalDate.parse(flight[3]), LocalDate.parse(flight[5]), flight[4], flight[6]);
                FlightsForWantedVacation.add(f);
            }
            Vacation wantedVacation = new Vacation(userVacation[1], LocalDate.parse(userVacation[4]), LocalDate.parse(userVacation[5]), Integer.parseInt(userVacation[6]), Integer.parseInt(userVacation[7]), userVacation[8].equals("true"), userVacation[2], userVacation[3], (Integer.parseInt(userVacation[12]) > 0), Integer.parseInt(userVacation[12]), Vacation.Tickets_Type.valueOf(userVacation[9]), FlightsForWantedVacation, Vacation.Flight_Type.valueOf(userVacation[11]), Vacation.Vacation_Type.valueOf(userVacation[10]), userVacation[13].equals("true"), Integer.parseInt(userVacation[14]));
            VacationSell wantedVacationSell = new VacationSell(Integer.parseInt(userVacation[0]), wantedVacation, VacationSell.Vacation_Status.valueOf(userVacation[15]));
            for (String[] request : requestByVacation) {
                if(!request[3].equals(TradeARequest.Request_Status.pending.name()))//create the offered vacation Object\
                    continue;
                String offeredVacationID = request[1];
                String[] offeredVacationDetails = vacationsTable.getVacationsString(offeredVacationID);
                IDflightOfvacation = flightsToVacationsTable.getFlightsOfVacationsString(offeredVacationID);
                List<Flight> FlightsForOfferedVacation = new ArrayList<>();
                for (String[] IDflight : IDflightOfvacation) {
                    String[] flight = flightsTable.getFlightByID(IDflight[1]);
                    Flight f = new Flight(flight[7], flight[1], flight[2], LocalDate.parse(flight[3]), LocalDate.parse(flight[5]), flight[4], flight[6]);
                    FlightsForOfferedVacation.add(f);
                }
                Vacation offeredVacation = new Vacation(offeredVacationDetails[1], LocalDate.parse(offeredVacationDetails[4]), LocalDate.parse(offeredVacationDetails[5]), Integer.parseInt(offeredVacationDetails[6]), Integer.parseInt(offeredVacationDetails[7]), offeredVacationDetails[8].equals("true"), offeredVacationDetails[2], offeredVacationDetails[3], (Integer.parseInt(offeredVacationDetails[12]) > 0), Integer.parseInt(offeredVacationDetails[12]), Vacation.Tickets_Type.valueOf(offeredVacationDetails[9]), FlightsForOfferedVacation, Vacation.Flight_Type.valueOf(offeredVacationDetails[11]), Vacation.Vacation_Type.valueOf(offeredVacationDetails[10]), offeredVacationDetails[13].equals("true"), Integer.parseInt(offeredVacationDetails[14]));
                VacationSell offeredVacationSell = new VacationSell(Integer.parseInt(offeredVacationDetails[0]), offeredVacation, VacationSell.Vacation_Status.valueOf(offeredVacationDetails[15]));
                TradeARequest tradeARequest = new TradeARequest(Integer.parseInt(request[0]), ARequest.Request_Status.valueOf(request[3]), wantedVacationSell, offeredVacationSell);
                ans.add(tradeARequest);
            }
        }
        return ans;
    }

    public boolean acceptPurchaseRequest(int requestId) {
        boolean ans = purchaseRequestsTable.acceptRequest(requestId);
        if (!ans)
            return false;
        String[] requestDetails = purchaseRequestsTable.getRequestDetail(requestId);
        String vacationID = requestDetails[2];
        String[] vacationValues = vacationsTable.getVacationsString(vacationID);
        vacationValues[15] = VacationSell.Vacation_Status.sold.name();
        vacationsTable.updateVacation(vacationID, vacationValues);
        List<String[]> toReject = purchaseRequestsTable.getRequestForVacations(vacationID);
        for (String[] tr : toReject) {
            int requestNum = Integer.parseInt(tr[0]);
            if (requestNum != requestId)
                ans = ans && purchaseRequestsTable.rejectRequest(requestNum);
        }
        toReject = tradeRequestsTable.getRequestByWantedVacationID(vacationID);
        toReject.addAll(tradeRequestsTable.getRequestByOfferdVacationID(vacationID));
        for (String[] tr : toReject) {
            int requestNum = Integer.parseInt(tr[0]);
            ans = ans && tradeRequestsTable.rejectRequest(requestNum);
        }
        return ans;
    }

    public boolean rejectPurchaseRequest(int requestId) {
        return purchaseRequestsTable.rejectRequest(requestId);
    }

    public boolean acceptTradeRequest(int requestId) {
        boolean ans = tradeRequestsTable.acceptRequest(requestId);
        if (!ans)
            return false;
        String[] vacations = tradeRequestsTable.getVacationsIDs(requestId);
        String[] offeredVacation = vacationsTable.getVacationsString(vacations[0]);
        offeredVacation[15] = VacationSell.Vacation_Status.sold.name();
        String[] wantedVacation = vacationsTable.getVacationsString(vacations[1]);
        wantedVacation[15] = VacationSell.Vacation_Status.sold.name();
        ans = vacationsTable.updateVacation(vacations[0], offeredVacation);
        ans = ans && vacationsTable.updateVacation(vacations[1], wantedVacation);

        List<String[]> toReject = tradeRequestsTable.getRequestByWantedVacationID(vacations[1]);
        toReject.addAll(tradeRequestsTable.getRequestByOfferdVacationID(vacations[1]));
        for (String[] tr : toReject) {
            int requestNum = Integer.parseInt(tr[0]);
            if (requestId != requestNum)
                ans = ans && tradeRequestsTable.rejectRequest(requestNum);
        }
        toReject = tradeRequestsTable.getRequestByOfferdVacationID(vacations[0]);
        toReject.addAll(tradeRequestsTable.getRequestByWantedVacationID(vacations[0]));
        for (String[] tr : toReject) {
            int requestNum = Integer.parseInt(tr[0]);
            if (requestId != requestNum)
                ans = ans && tradeRequestsTable.rejectRequest(requestNum);
        }

        toReject = purchaseRequestsTable.getRequestForVacations(vacations[1]);
        toReject.addAll(purchaseRequestsTable.getMyRequestsSting(vacations[0]));
        for (String[] tr : toReject) {
            int requestNum = Integer.parseInt(tr[0]);
                ans = ans && purchaseRequestsTable.rejectRequest(requestNum);
        }
        return ans;
    }

    public boolean rejectTradeRequest(int requestId) {
        return tradeRequestsTable.rejectRequest(requestId);
    }

    public List<VacationSell> getMyVacations(String username) {
        List<VacationSell> vacationSells = new ArrayList<>();
        List<String[]> userVacationDetails = vacationsTable.getVacationsByUsernameString(username);
        for (String[] userVacationDetail : userVacationDetails) {
            List<String[]> IDflightOfvacation = flightsToVacationsTable.getFlightsOfVacationsString(userVacationDetail[0]);//This is for create the Vacation object of the offered vacation
            List<Flight> FlightsForWantedVacation = new ArrayList<>();
            for (String[] IDflight : IDflightOfvacation) {
                String[] flight = flightsTable.getFlightByID(IDflight[1]);
                Flight f = new Flight(flight[7], flight[1], flight[2], LocalDate.parse(flight[3]), LocalDate.parse(flight[5]), flight[4], flight[6]);
                FlightsForWantedVacation.add(f);
            }
            Vacation wantedVacation = new Vacation(userVacationDetail[1], LocalDate.parse(userVacationDetail[4]), LocalDate.parse(userVacationDetail[5]), Integer.parseInt(userVacationDetail[6]), Integer.parseInt(userVacationDetail[7]), userVacationDetail[8].equals("true"), userVacationDetail[2], userVacationDetail[3], (Integer.parseInt(userVacationDetail[12]) > 0), Integer.parseInt(userVacationDetail[12]), Vacation.Tickets_Type.valueOf(userVacationDetail[9]), FlightsForWantedVacation, Vacation.Flight_Type.valueOf(userVacationDetail[11]), Vacation.Vacation_Type.valueOf(userVacationDetail[10]), userVacationDetail[13].equals("true"), Integer.parseInt(userVacationDetail[14]));
            VacationSell wantedVacationSell = new VacationSell(Integer.parseInt(userVacationDetail[0]), wantedVacation, VacationSell.Vacation_Status.valueOf(userVacationDetail[15]));
            vacationSells.add(wantedVacationSell);
        }
        return vacationSells;
    }
}
