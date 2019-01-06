package Model;


import Model.Database.Vacation4UDatabase;
import Model.Objects.*;
import Model.Requests.PurchaseARequest;
import Model.Requests.PurchaseRequestData;
import Model.Requests.TradeARequest;

import java.time.LocalDate;
import java.util.List;

public class Model implements IModel {
    //attributes
    private String databaseName;

    //Enums...
    public enum UsersfieldNameEnum {
        Username, Password, Birthday, FirstName, LastName, City, State;
    }


    public enum PurchaseRequestsfieldNameEnum {PurchaseRequest_id, Requester_Username, Vacation_id, Request_Status;}

    public enum FlightsToVacationsfieldNameEnum {Vacation_id, Flight_id}

    public enum VacationsfieldNameEnum {Vacation_id, Publisher_Username, Source_Country, Destination_Country, From_Date, To_Date, Price_Per_ticket, Num_Of_Passengers, Can_Buy_less_Tickets, Tickets_Type, Vacation_Type, Flight_Type, Baggage_Limit, Lodging_Included, Lodging_Rating, Vacation_Status;}

//    public enum PurchasesfieldNameEnum {Username, VacationID, PaymentCompany, CardNumber, CVV, ValidDate, UserID, FirstName, LastName;}

    public enum FlightsfieldNameEnum {FlightID, OriginAirport, DestinationAirport, DepartureDate, DepartureTime, ArrivalDate, ArrivalTime, FlightComapny;}

    public enum PaymentfieldsNameEnum {PaymentID, Username, VacationID, Payment_Method;}

    public enum VisePaymentfieldsEnum {PaymentID, CardNumber, CVV, ValidDate, UserID, FirstName, LastName;}

    public enum PaypalPaymentfieldsEnum {PaymentID, email, password;}


    public enum tableNameEnum {Users_table, Vacations_Table, Purchases_Table, Flights_table, FlightsToVacations_Table, PurchaseRequests_Table, Payments_Table, VisaPayments_Table, Paypalpayments_Table;}

    Vacation4UDatabase vacation4UDatabase;

    //constructor
    public Model() {
        vacation4UDatabase = new Vacation4UDatabase();
    }

    // private functions (generics)
//    private void createNewDatabase() {
//
//        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
//
//        try (Connection conn = DriverManager.getConnection(url)) {
//            conn.close();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        createNewUsersTable();
////        createNewPurchasesTable();
//        createNewFlightsTable();
//        createNewVacationsTable();
//        createNewPurchaseRequestTable();
//        createNewFlightsToVacationsTable();
//        createNewPaymentsTable();
//        createNewVisaPaymentTable();
//        createPaypalPaymentTavle();
//    }//creating a new database with the parameter name
//
//    public void createNewUsersTable() {
//        // SQLite connection string
//        Connection c = null;
//        Statement stmt = null;
//        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
//
//        try {
//            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE IF NOT EXISTS Users_Table (\n"
//                    + "Username text PRIMARY KEY,\n"
//                    + "	Password text NOT NULL,\n"
//                    + "	Birthday text NOT NULL,\n"
//                    + "	FirstName text NOT NULL,\n"
//                    + "	LastName text NOT NULL,\n"
//                    + "	City text NOT NULL,\n"
//                    + "	State text NOT NULL\n"
//                    + ");";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }//creating a new users table
//
//    public void createNewPurchasesTable() {
//        Connection c = null;
//        Statement stmt = null;
//        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
//
//        try {
//            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE IF NOT EXISTS Purchases_Table (\n"
//                    + "Username text NOT NULL,\n"
//                    + "VacationID text NOT NULL,\n"
//                    + "PaymentCompany text NOT NULL,\n"
//                    + "CardNumber text,\n"
//                    + "CVV text,\n"
//                    + "ValidDate text,\n"
//                    + "UserID text,\n"
//                    + "FirstName text,\n"
//                    + "LastName text,\n"
//                    + "CONSTRAINT PK_Person PRIMARY KEY (Username,VacationID)"
//                    + ");";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void createNewFlightsTable() {
//        Connection c = null;
//        Statement stmt = null;
//        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
//
//        try {
//            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE IF NOT EXISTS Flights_Table (\n"
//                    + "FlightID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
//                    + "OriginAirport text NOT NULL,\n"
//                    + "DestinationAirport text NOT NULL,\n"
//                    + "DepartureDate text,\n"
//                    + "DepartureTime text,\n"
//                    + "ArrivalDate text,\n"
//                    + "ArrivalTime text,\n"
//                    + "FlightComapny text\n"
//                    + ");";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void createNewVacationsTable() {
//        // SQLite connection string
//        Connection c = null;
//        Statement stmt = null;
//        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
//
//        try {
//            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE IF NOT EXISTS Vacations_Table (\n"
//                    + "Vacation_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
//                    + "Publisher_Username text NOT NULL,\n"
//                    + "Source_Country text NOT NULL,\n"
//                    + "Destination_Country text NOT NULL,\n"
//                    + "From_Date text NOT NULL,\n"
//                    + "To_Date text NOT NULL,\n"
//                    + "Price_Per_ticket text NOT NULL,\n"
//                    + "Num_Of_Passengers text NOT NULL,\n"
//                    + "Can_Buy_less_Tickets text NOT NULL,\n"
//                    + "Tickets_Type text NOT NULL,\n"
//                    + "Vacation_Type text NOT NULL,\n"
//                    + "Flight_Type text NOT NULL,\n"
//                    + "Baggage_Limit text NOT NULL,\n"
//                    + "Lodging_Included text NOT NULL,\n"
//                    + "Lodging_Rating text NOT NULL,\n"
//                    + "Vacation_Status text NOT NULL\n"
//                    + ");";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }//creating a new vacations table
//
//    public void createNewPurchaseRequestTable() {
//        // SQLite connection string
//        Connection c = null;
//        Statement stmt = null;
//        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
//
//        try {
//            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE IF NOT EXISTS PurchaseRequests_Table (\n"
//                    + "PurchaseRequest_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
//                    + "Requester_Username text NOT NULL,\n"
//                    + "Vacation_id text NOT NULL,\n"
//                    + "Request_Status text NOT NULL\n"
//                    + ");";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }//creating a new PurchaseARequest Table
//
//    public void createNewFlightsToVacationsTable() {
//        // SQLite connection string
//        Connection c = null;
//        Statement stmt = null;
//        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
//
//        try {
//            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE IF NOT EXISTS FlightsToVacations_Table (\n"
//                    + "Vacation_id text NOT NULL,\n"
//                    + "Flight_id text NOT NULL,\n"
//                    + "	CONSTRAINT PK_FTV PRIMARY KEY (Vacation_id,Flight_id)\n"
//                    + ");";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }//creating a new FlightsToVacations Table
//
//    public void createNewPaymentsTable() {
//        // SQLite connection string
//        Connection c = null;
//        Statement stmt = null;
//        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
//
//        try {
//            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE IF NOT EXISTS Payments_Table (\n"
//                    + "PaymentID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
//                    + "Username text NOT NULL,\n"
//                    + "VacationID text NOT NULL,\n"
//                    + "Payment_Method text NOT NULL\n"
//                    + ");";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void createNewVisaPaymentTable() {
//        // SQLite connection string
//        Connection c = null;
//        Statement stmt = null;
//        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
//
//        try {
//            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE IF NOT EXISTS VisaPayments_Table (\n"
//                    + "PaymentID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
//                    + "CardNumber text,\n"
//                    + "CVV text,\n"
//                    + "ValidDate text,\n"
//                    + "UserID text,\n"
//                    + "FirstName text,\n"
//                    + "LastName text\n"
//                    + ");";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void createPaypalPaymentTavle() {
//        Connection c = null;
//        Statement stmt = null;
//        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
//
//        try {
//            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE IF NOT EXISTS Paypalpayments_Table (\n"
//                    + "PaymentID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
//                    + "email text NOT NULL,\n"
//                    + "password text\n"
//                    + ");";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    //
    //functional functions:
    //


//    private void insertQuery(String table_name, Class<? extends Enum<?>> tableEnum, String[] insert_values) throws SQLException {
//        String[] field_array = getNames(tableEnum);
//        String sql = "INSERT INTO " + table_name + "(";
//        for (int i = 0; i < field_array.length; i++) {
//            sql += field_array[i] + ",";
//        }
//        sql = sql.substring(0, sql.length() - 1);
//        sql += ") VALUES(";
//        for (int i = 0; i < field_array.length; i++) {
//            if (i != field_array.length - 1)
//                sql += "?,";
//            else
//                sql += "?)";
//        }
//
//        Connection conn = connect();
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        for (int i = 0; i < field_array.length; i++) {
//            if (i >= insert_values.length) {
//                pstmt.setString(i + 1, "");
//            } else {
//                pstmt.setString(i + 1, insert_values[i]);
//            }
//
//        }
//        pstmt.executeUpdate();
//
//    }//insert query pattern
//
//    private List<String[]> selectQuery(String table_name, String where_condition) {
//        String sql = "SELECT *" + " FROM " + table_name + " WHERE " + where_condition;
//        try (Connection conn = connect();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            ResultSet rs = pstmt.executeQuery();
//            // loop through the result set
//            int nCol = rs.getMetaData().getColumnCount();
//            List<String[]> table = new ArrayList<>();
//            while (rs.next()) {
//                String[] row = new String[nCol];
//                for (int iCol = 1; iCol <= nCol; iCol++) {
//                    Object obj = rs.getObject(iCol);
//                    row[iCol - 1] = (obj == null) ? null : obj.toString();
//                }
//                table.add(row);
//            }
//            return table;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }//select query pattern
//
//    private void updateQuery(String table_name, Class<? extends Enum<?>> tableEnum, String[] update_values, String where_condition) throws SQLException {
//        String[] fields_array = getNames(tableEnum);
//        String sql = "UPDATE " + table_name + " SET ";
//        for (int i = 0; i < fields_array.length; i++) {
//            sql += fields_array[i] + " = ? ,";
//        }
//        sql = sql.substring(0, sql.length() - 1) + " ";
//        sql += "WHERE " + where_condition;
//
//        Connection conn = this.connect();
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//
//        // set the corresponding param
//        for (int j = 0; j < fields_array.length; j++) {
//            if (j >= update_values.length) {
//                pstmt.setString(j + 1, "");
//            } else {
//                pstmt.setString(j + 1, update_values[j]);
//            }
//
//        }
//        // update
//        pstmt.executeUpdate();
//
//    }//update query pattern
//
//    private static String[] getNames(Class<? extends Enum<?>> e) {
//        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
//    }//converting enum to String array
//
//    private void deleteQuery(String table_name, String where_condition) {
//        String sql = "DELETE FROM " + table_name + " WHERE " + where_condition;
//
//        try (Connection conn = connect();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            // execute the delete statement
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }//delete query pattern
//
//    private Connection connect() {
//        // SQLite connection string
//        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(url);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return conn;
//    }//connecting to the database
    //public Functions:

    // Users_Table

    @Override
    public User getUser(String username_val) {
        return vacation4UDatabase.getUser(username_val);
    }

    @Override
    public boolean deleteUser(String username) {
        return vacation4UDatabase.deleteUser(username);
    }

    @Override
    public boolean userExist(String username) {
        return vacation4UDatabase.userExist(username);
    }


    //TODO: Functions.
    @Override
    public boolean createUser(User user) {
        return vacation4UDatabase.createUser(user);
    }

    @Override
    public boolean updateUserInfo(String username, User user) {
        return vacation4UDatabase.updateUserInfo(username, user);
    }

    @Override
    public boolean publishVacation(Vacation vacation) {
        return vacation4UDatabase.publishVacation(vacation);
    }

    @Override
    public List<VacationSell> getVacations(String username, String flightCompany, LocalDate fromDate, LocalDate toDate, boolean baggage, Integer baggageMin, Integer ticketsNum, Vacation.Tickets_Type tickets_type, Integer maxPricePerTicket, String sourceCountry, String destCountry, Vacation.Vacation_Type vacation_type, Vacation.Flight_Type flight_type, boolean hospitalityIncluded, Integer minHospitalityRank) {
        return vacation4UDatabase.getVacations(username, flightCompany, fromDate, toDate, baggage, baggageMin, ticketsNum, tickets_type, maxPricePerTicket, sourceCountry, destCountry, vacation_type, flight_type, hospitalityIncluded, minHospitalityRank);
    }

    @Override
    public boolean sendRequest(PurchaseRequestData purchaseRequestData) {
        return vacation4UDatabase.sendRequest(purchaseRequestData);
    }

    @Override
    public List<PurchaseARequest> getMyPurchaseRequests(String username) {
        return vacation4UDatabase.getMyPurchaseRequests(username);
    }

    @Override
    public List<PurchaseARequest> getReceivedPurchaseRequests(String username) {
        return vacation4UDatabase.getReceivedPurchaseRequests(username);
    }

    public List<TradeARequest> getMyTradeRequests(String username) {
        return vacation4UDatabase.getMyTradeRequests(username);
    }

    public List<TradeARequest> getReceivedTradeRequests(String username) {
        return vacation4UDatabase.getReceivedTradeRequests(username);
    }

    public List<VacationSell> getMyVacation(String username)
    {
        return vacation4UDatabase.getMyVacation(username);
    }

    @Override
    public boolean acceptPurchaseRequest(int requestId) {
        return vacation4UDatabase.acceptPurchaseRequest(requestId);
    }

    @Override
    public boolean rejectPurchaseRequest(int requestId) {
        return vacation4UDatabase.rejectPurchaseRequest(requestId);
    }

    public boolean acceptTradeRequest(int requestId) {
        return vacation4UDatabase.acceptTradeRequest(requestId);
    }

    public boolean rejectTradeRequest(int requestId) {
        return vacation4UDatabase.rejectTradeRequest(requestId);
    }

//    @Override
//    public boolean payForVacation(int requestId, Payment payment) {
//        String usernamePaying = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.PurchaseRequest_id.toString() + "='" + requestId + "'").get(0)[1];
//        String[] requestInfo = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.PurchaseRequest_id.toString() + "='" + requestId + "'").get(0);
//        String vacationId = requestInfo[2];
//        String paymentMethod = "";
//        if (payment.isVisa())
//            paymentMethod = "Visa";
//        else if (payment.isPaypal())
//            paymentMethod = "Paypal";
//        String[] values = {null, usernamePaying, vacationId, paymentMethod};
//        try {
//            insertQuery(tableNameEnum.Payments_Table.toString(), PaymentfieldsNameEnum.class, values);
//        } catch (SQLException e) {
//            return false;
//        }
//        String PaymentID = selectQuery(tableNameEnum.Payments_Table.toString(), PaymentfieldsNameEnum.Username.toString() + "='" + usernamePaying + "' AND " + PaymentfieldsNameEnum.VacationID + "='" + vacationId + "' AND " + PaymentfieldsNameEnum.Payment_Method + "='" + paymentMethod + "'").get(0)[0];
//        List<String[]> toReject = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.Vacation_id + "='" + vacationId + "'");
//        for (String[] tr : toReject) {
//            int requestNum = Integer.parseInt(tr[0]);
//            if (requestNum != requestId)
//                rejectPurchaseRequest(requestNum);
//        }
//        PayaplPayment ppp;
//        VisaPayment vp;
//        boolean successPay = false;
//        if (payment.isPaypal()) {
//            ppp = (PayaplPayment) payment;
//            String[] newvalues = {PaymentID, ppp.getEmail(), ppp.getPassword()};
//            try {
//                insertQuery(tableNameEnum.Paypalpayments_Table.toString(), PaypalPaymentfieldsEnum.class, newvalues);
//            } catch (SQLException e) {
//                e.printStackTrace();
//                return false;
//            }
//            successPay = true;
//        }
//        if (payment.isVisa()) {
//            vp = (VisaPayment) payment;
//            String[] newvalues = {PaymentID, "" + vp.getCardNumber(), "" + vp.getThreeNumbers(), vp.getDate().toString(), vp.getOwnerId(), vp.getOwnerFirstName(), vp.getOwnerLastName()};
//            try {
//                insertQuery(tableNameEnum.VisaPayments_Table.toString(), VisePaymentfieldsEnum.class, newvalues);
//            } catch (SQLException e) {
//                e.printStackTrace();
//                return false;
//            }
//            successPay = true;
//        }
//        if (successPay) {
//            String[] vacationInfo = selectQuery(tableNameEnum.Vacations_Table.name(), VacationsfieldNameEnum.Vacation_id + "='" + vacationId + "'").get(0);
//            vacationInfo[15] = VacationSell.Vacation_Status.sold.name();
//            try {
//                updateQuery(tableNameEnum.Vacations_Table.name(), VacationsfieldNameEnum.class, vacationInfo, VacationsfieldNameEnum.Vacation_id + "='" + vacationId + "'");
//                requestInfo[3] = PurchaseARequest.Request_Status.done.name();
//                updateQuery(tableNameEnum.PurchaseRequests_Table.name(), PurchaseRequestsfieldNameEnum.class, requestInfo, PurchaseRequestsfieldNameEnum.PurchaseRequest_id.name() + "='" + requestId + "'");
//            } catch (SQLException e) {
//                e.printStackTrace();
//                return false;
//            }
//            return true;
//        }
//        return false;
//    }


    public boolean UsersTable_checkPassword(String Username_val, String Password_val) {
        return vacation4UDatabase.UsersTable_checkPassword(Username_val, Password_val);
    }
}
