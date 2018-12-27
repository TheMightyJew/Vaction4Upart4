package Model;


import Model.Objects.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

    //constructor
    public Model(String databaseName) {
        this.databaseName = databaseName + ".db";
        createNewDatabase();
    }

    // private functions (generics)
    private void createNewDatabase() {

        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try (Connection conn = DriverManager.getConnection(url)) {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        createNewUsersTable();
//        createNewPurchasesTable();
        createNewFlightsTable();
        createNewVacationsTable();
        createNewPurchaseRequestTable();
        createNewFlightsToVacationsTable();
        createNewPaymentsTable();
        createNewVisaPaymentTable();
        createPaypalPaymentTable();
    }//creating a new database with the parameter name

    public void createNewUsersTable() {
        // SQLite connection string
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Users_Table (\n"
                    + "Username text PRIMARY KEY,\n"
                    + "	Password text NOT NULL,\n"
                    + "	Birthday text NOT NULL,\n"
                    + "	FirstName text NOT NULL,\n"
                    + "	LastName text NOT NULL,\n"
                    + "	City text NOT NULL,\n"
                    + "	State text NOT NULL\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//creating a new users table

    public void createNewPurchasesTable() {
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Purchases_Table (\n"
                    + "Username text NOT NULL,\n"
                    + "VacationID text NOT NULL,\n"
                    + "PaymentCompany text NOT NULL,\n"
                    + "CardNumber text,\n"
                    + "CVV text,\n"
                    + "ValidDate text,\n"
                    + "UserID text,\n"
                    + "FirstName text,\n"
                    + "LastName text,\n"
                    + "CONSTRAINT PK_Person PRIMARY KEY (Username,VacationID)"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNewFlightsTable() {
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Flights_Table (\n"
                    + "FlightID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "OriginAirport text NOT NULL,\n"
                    + "DestinationAirport text NOT NULL,\n"
                    + "DepartureDate text,\n"
                    + "DepartureTime text,\n"
                    + "ArrivalDate text,\n"
                    + "ArrivalTime text,\n"
                    + "FlightComapny text\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNewVacationsTable() {
        // SQLite connection string
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Vacations_Table (\n"
                    + "Vacation_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "Publisher_Username text NOT NULL,\n"
                    + "Source_Country text NOT NULL,\n"
                    + "Destination_Country text NOT NULL,\n"
                    + "From_Date text NOT NULL,\n"
                    + "To_Date text NOT NULL,\n"
                    + "Price_Per_ticket text NOT NULL,\n"
                    + "Num_Of_Passengers text NOT NULL,\n"
                    + "Can_Buy_less_Tickets text NOT NULL,\n"
                    + "Tickets_Type text NOT NULL,\n"
                    + "Vacation_Type text NOT NULL,\n"
                    + "Flight_Type text NOT NULL,\n"
                    + "Baggage_Limit text NOT NULL,\n"
                    + "Lodging_Included text NOT NULL,\n"
                    + "Lodging_Rating text NOT NULL,\n"
                    + "Vacation_Status text NOT NULL\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//creating a new vacations table

    public void createNewPurchaseRequestTable() {
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
    }//creating a new PurchaseRequest Table

    public void createNewFlightsToVacationsTable() {
        // SQLite connection string
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
    }//creating a new FlightsToVacations Table

    public void createNewPaymentsTable() {
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

    public void createNewVisaPaymentTable() {
        // SQLite connection string
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS VisaPayments_Table (\n"
                    + "PaymentID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "CardNumber text,\n"
                    + "CVV text,\n"
                    + "ValidDate text,\n"
                    + "UserID text,\n"
                    + "FirstName text,\n"
                    + "LastName text\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPaypalPaymentTable() {
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Paypalpayments_Table (\n"
                    + "PaymentID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "email text NOT NULL,\n"
                    + "password text\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //
    //functional functions:
    //


    private void insertQuery(String table_name, Class<? extends Enum<?>> tableEnum, String[] insert_values) throws SQLException {
        String[] field_array = getNames(tableEnum);
        String sql = "INSERT INTO " + table_name + "(";
        for (int i = 0; i < field_array.length; i++) {
            sql += field_array[i] + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ") VALUES(";
        for (int i = 0; i < field_array.length; i++) {
            if (i != field_array.length - 1)
                sql += "?,";
            else
                sql += "?)";
        }

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < field_array.length; i++) {
            if (i >= insert_values.length) {
                pstmt.setString(i + 1, "");
            } else {
                pstmt.setString(i + 1, insert_values[i]);
            }

        }
        pstmt.executeUpdate();

    }//insert query pattern

    private List<String[]> selectQuery(String table_name, String where_condition) {
        String sql = "SELECT *" + " FROM " + table_name + " WHERE " + where_condition;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            // loop through the result set
            int nCol = rs.getMetaData().getColumnCount();
            List<String[]> table = new ArrayList<>();
            while (rs.next()) {
                String[] row = new String[nCol];
                for (int iCol = 1; iCol <= nCol; iCol++) {
                    Object obj = rs.getObject(iCol);
                    row[iCol - 1] = (obj == null) ? null : obj.toString();
                }
                table.add(row);
            }
            return table;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }//select query pattern

    private void updateQuery(String table_name, Class<? extends Enum<?>> tableEnum, String[] update_values, String where_condition) throws SQLException {
        String[] fields_array = getNames(tableEnum);
        String sql = "UPDATE " + table_name + " SET ";
        for (int i = 0; i < fields_array.length; i++) {
            sql += fields_array[i] + " = ? ,";
        }
        sql = sql.substring(0, sql.length() - 1) + " ";
        sql += "WHERE " + where_condition;

        Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // set the corresponding param
        for (int j = 0; j < fields_array.length; j++) {
            if (j >= update_values.length) {
                pstmt.setString(j + 1, "");
            } else {
                pstmt.setString(j + 1, update_values[j]);
            }

        }
        // update
        pstmt.executeUpdate();

    }//update query pattern

    private static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }//converting enum to String array

    private void deleteQuery(String table_name, String where_condition) {
        String sql = "DELETE FROM " + table_name + " WHERE " + where_condition;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//delete query pattern

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }//connecting to the database
    //public Functions:

    // Users_Table
    @Override
    public void createUser(String Username_val, String Password_val, String Birthday_val, String FirstName_val, String LastName_val, String City_val, String State_val) {
        String[] values = {Username_val, Password_val, Birthday_val, FirstName_val, LastName_val, City_val, State_val};
        try {
            insertQuery("Users_Table", UsersfieldNameEnum.class, values);
        } catch (Exception ignore) {
        }
    }

    @Override
    public User getUser(String Username_val) {
        List<String[]> result = selectQuery("Users_Table", UsersfieldNameEnum.Username + " = '" + Username_val + "'");
        if (result.size() != 1)
            return null;
        else {
            String[] ans = result.get(0);
            LocalDate bDate = LocalDate.parse(ans[2]);
            return new User(ans[0], ans[1], bDate, ans[3], ans[4], ans[5], ans[6]);
        }
    }

    @Override
    public void updateUserInfo(String Username_key, String Username_val, String Password_val, String Birthday_val, String FirstName_val, String LastName_val, String City_val, String State_val) {
        String[] values = {Username_val, Password_val, Birthday_val, FirstName_val, LastName_val, City_val, State_val};
        try {
            updateQuery(tableNameEnum.Users_table.toString(), UsersfieldNameEnum.class, values, UsersfieldNameEnum.Username.toString() + " = '" + Username_key + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteUser(String username) {
        try {
            deleteQuery(tableNameEnum.Users_table.toString(), UsersfieldNameEnum.Username + " = '" + username + "'");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean userExist(String username) {
        if (getUser(username) == null)
            return false;
        else
            return true;
    }


    //TODO: Functions.
    @Override
    public boolean createUser(User user) {
        try {
            String[] values = {user.getUsername(), user.getPassword(), user.getBirth_Date().toString(), user.getFirst_Name(), user.getLast_Name(), user.getCity(), user.getCountry()};
            insertQuery("Users_Table", UsersfieldNameEnum.class, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateUserInfo(String username, User user) {
        try {
            String Birthday_val = user.getBirth_Date().toString();
            String[] values = {user.getUsername(), user.getPassword(), Birthday_val, user.getFirst_Name(), user.getLast_Name(), user.getCity(), user.getCountry()};
            updateQuery(tableNameEnum.Users_table.name(), UsersfieldNameEnum.class, values, UsersfieldNameEnum.Username.toString() + " = '" + username + "'");
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public boolean publishVacation(Vacation vacation) {
        int baggage = vacation.isBaggage_Included() ? 1 : 0;
        if (baggage > 0)
            baggage = vacation.getBaggageLimit();
        try {
            insertQuery(tableNameEnum.Vacations_Table.name(), VacationsfieldNameEnum.class, new String[]{null, vacation.getSeller_username(), vacation.getSourceCountry(), vacation.getDestinationCountry(), vacation.getFromDate().toString(), vacation.getToDate().toString(), String.valueOf(vacation.getPrice_Per_Ticket()),
                    String.valueOf(vacation.getTickets_Quantity()), String.valueOf(vacation.isCanBuyLess()), vacation.getTicketsType().name(), vacation.getVacation_type().name(), vacation.getFlight_Type().name(), String.valueOf(baggage), String.valueOf(vacation.isHospitality_Included()), String.valueOf(vacation.getHospitality_Rank()), VacationSell.Vacation_Status.available.name()});
            String vacationID = selectQuery(tableNameEnum.Vacations_Table.name(), VacationsfieldNameEnum.Publisher_Username.name() + "='" + vacation.getSeller_username() + "' AND " + VacationsfieldNameEnum.Source_Country.name() + "='" + vacation.getSourceCountry() + "' AND " + VacationsfieldNameEnum.Destination_Country.name() + "='" + vacation.getDestinationCountry()
                    + "' AND " + VacationsfieldNameEnum.From_Date + "='" + vacation.getFromDate().toString() + "' AND " + VacationsfieldNameEnum.To_Date + "='" + vacation.getToDate().toString() + "' AND " + VacationsfieldNameEnum.Price_Per_ticket.name() + "='" + vacation.getPrice_Per_Ticket() + "' AND " + VacationsfieldNameEnum.Num_Of_Passengers.name() + "='" + vacation.getTickets_Quantity() + "' AND " + VacationsfieldNameEnum.Can_Buy_less_Tickets.name() + "='" + vacation.isCanBuyLess() + "' AND "
                    + VacationsfieldNameEnum.Tickets_Type.name() + "='" + vacation.getTicketsType().name() + "' AND " + VacationsfieldNameEnum.Vacation_Type.name() + "='" + vacation.getVacation_type().name() + "' AND " + VacationsfieldNameEnum.Flight_Type.name() + "='" + vacation.getFlight_Type().name() + "' AND " + VacationsfieldNameEnum.Baggage_Limit.name() + "='" + baggage + "' AND "
                    + VacationsfieldNameEnum.Lodging_Included.name() + "='" + vacation.isHospitality_Included() + "' AND " + VacationsfieldNameEnum.Lodging_Rating.name() + "='" + vacation.getHospitality_Rank() + "'").get(0)[0];
            List<String[]> records_of_flight = selectQuery(tableNameEnum.Flights_table.name(), "'1'='1'");
            int nextFlightID = records_of_flight.size() + 1;
            for (Flight flight : vacation.getFlights()) {
                insertQuery(tableNameEnum.Flights_table.name(), FlightsfieldNameEnum.class, new String[]{null, flight.getSourceAirPort(), flight.getDestinationAirPort(), flight.getDepartDate().toString(), flight.getDepartHour(), flight.getLandDate().toString(), flight.getLandHour(), flight.getFlightCompany()});
                String flightID = selectQuery(tableNameEnum.Flights_table.name(), FlightsfieldNameEnum.OriginAirport.name() + "='" + flight.getSourceAirPort() + "' AND " + FlightsfieldNameEnum.DestinationAirport.name() + "='" + flight.getDestinationAirPort() + "' AND " + FlightsfieldNameEnum.DepartureDate.name() + "='" + flight.getDepartDate().toString() + "' AND "
                        + FlightsfieldNameEnum.DepartureTime.name() + "='" + flight.getDepartHour() + "' AND " + FlightsfieldNameEnum.ArrivalDate.name() + "='" + flight.getLandDate().toString() + "' AND " + FlightsfieldNameEnum.ArrivalTime.name() + "='" + flight.getLandHour() + "' AND " + FlightsfieldNameEnum.FlightComapny.name() + "='" + flight.getFlightCompany() + "'").get(0)[0];
                insertQuery(tableNameEnum.FlightsToVacations_Table.name(), FlightsToVacationsfieldNameEnum.class, new String[]{String.valueOf(vacationID), String.valueOf(flightID)});
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<VacationSell> getVacations(String username, String flightCompany, LocalDate fromDate, LocalDate toDate, boolean baggage, Integer baggageMin, Integer ticketsNum, Vacation.Tickets_Type tickets_type, Integer maxPricePerTicket, String sourceCountry, String destCountry, Vacation.Vacation_Type vacation_type, Vacation.Flight_Type flight_type, boolean hospitalityIncluded, Integer minHospitalityRank) {
        String sql = "SELECT Vacations_Table.Vacation_id,Vacations_Table.Publisher_Username,Vacations_Table.Price_Per_ticket,Vacations_Table.Num_Of_Passengers,Vacations_Table.Can_Buy_less_Tickets,Vacations_Table.Source_Country,Vacations_Table.Destination_Country,Vacations_Table.From_Date,Vacations_Table.To_Date,Vacations_Table.Baggage_Limit,Vacations_Table.Tickets_Type,Vacations_Table.Flight_Type,Vacations_Table.Vacation_Type,Vacations_Table.Lodging_Included,Vacations_Table.Lodging_Rating,Vacations_Table.Vacation_Status,Flights_table.FlightComapny,Flights_table.DepartureDate,Flights_table.DepartureTime,Flights_table.ArrivalDate,Flights_table.ArrivalTime,Flights_table.OriginAirport,Flights_table.DestinationAirport\n" +
                "FROM (((FlightsToVacations_Table \n" +
                "INNER JOIN Vacations_Table ON FlightsToVacations_Table.Vacation_id = Vacations_Table.Vacation_id)\n" +
                "INNER JOIN Flights_table ON Flights_table.FlightID = FlightsToVacations_Table.Flight_id)\n" +
                "INNER JOIN(" +
                "select Vacations_Table.Vacation_id\n" +
                "from Vacations_Table\n"+
                "EXCEPT\n"+
                "SELECT Vacations_Table.Vacation_id\n" +
                "FROM PurchaseRequests_Table,Vacations_Table\n"+
                "WHERE "+ PurchaseRequestsfieldNameEnum.Requester_Username.name()+"='"+username+"'))\n"+
                "ORDER BY Vacations_Table.Vacation_id ASC;";
        List<VacationSell> optinalVacation = new ArrayList<>();
        List<VacationSell> ans = new ArrayList<>();
        List<String[]> table = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            // loop through the result set
            int nCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String[] row = new String[nCol];
                for (int iCol = 1; iCol <= nCol; iCol++) {
                    Object obj = rs.getObject(iCol);
                    row[iCol - 1] = (obj == null) ? null : obj.toString();
                }
                table.add(row);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        for (int i = 0; i < table.size(); i++) {
            int vacID = Integer.parseInt(table.get(i)[0]);
            List<Flight> flights_to_vacation = new ArrayList<>();

            while (i < table.size() && Integer.parseInt(table.get(i)[0]) == vacID) {
                String[] tableIniloop = table.get(i);
                Flight flight = new Flight(tableIniloop[16], tableIniloop[21], tableIniloop[22], LocalDate.parse(tableIniloop[17]), LocalDate.parse(tableIniloop[19]), tableIniloop[18], tableIniloop[20]);
                flights_to_vacation.add(flight);
                i++;
            }
            i--;
            String[] tableIni = table.get(i);
            Vacation vacation = new Vacation(tableIni[1], LocalDate.parse(tableIni[7]), LocalDate.parse(tableIni[8]), Integer.parseInt(tableIni[2]), Integer.parseInt(tableIni[3]), tableIni[4].equals("true"), tableIni[5], tableIni[6], (Integer.parseInt(tableIni[9]) > 0), Integer.parseInt(tableIni[9])
                    , Vacation.Tickets_Type.valueOf(tableIni[10]), flights_to_vacation, Vacation.Flight_Type.valueOf(tableIni[11]), Vacation.Vacation_Type.valueOf(tableIni[12]), tableIni[13].equals("true"), Integer.parseInt(tableIni[14]));
            VacationSell vacationSell = new VacationSell(vacID, vacation, VacationSell.Vacation_Status.valueOf(tableIni[15]));
            optinalVacation.add(vacationSell);
        }

        String sql1 = "SELECT PurchaseRequests_Table.Vacation_id\n" +
                "FROM PurchaseRequests_Table\n" +
                "WHERE "+ PurchaseRequestsfieldNameEnum.Requester_Username.name()+"<>'"+username+"';";
        List<String[]> userRequests = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql1)) {

            ResultSet rs = pstmt.executeQuery();
            // loop through the result set
            int nCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String[] row = new String[nCol];
                for (int iCol = 1; iCol <= nCol; iCol++) {
                    Object obj = rs.getObject(iCol);
                    row[iCol - 1] = (obj == null) ? null : obj.toString();
                }
                userRequests.add(row);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        for (int i = 0; i < optinalVacation.size(); i++) {
            VacationSell vacationSell = optinalVacation.get(i);
            Vacation vacation = vacationSell.getVacation();
            boolean existsFlightCompany = (flightCompany == null);
            for (Flight flight : vacation.getFlights()) {
                if (flight.getFlightCompany().equalsIgnoreCase(flightCompany))
                    existsFlightCompany = true;
            }
            if (!existsFlightCompany)
                continue;
            if (!(fromDate == null || vacation.getFromDate().compareTo(fromDate) >= 0))
                continue;
            if (!(toDate == null || vacation.getToDate().compareTo(toDate) <= 0))
                continue;
            if (baggage) {
                if (!(vacation.isBaggage_Included() && vacation.getBaggageLimit() >= baggageMin))
                    continue;
            }
            if (!(tickets_type == null || vacation.getTickets_Quantity() == ticketsNum || (vacation.isCanBuyLess() && vacation.getTickets_Quantity() >= ticketsNum)))
                continue;
            if (!(tickets_type == null || vacation.getTicketsType().name().equals(tickets_type.name())))
                continue;
            if (!(flight_type == null || vacation.getFlight_Type().name().equals(flight_type.name())))
                continue;
            if (!(maxPricePerTicket == null || vacation.getPrice_Per_Ticket() <= maxPricePerTicket))
                continue;
            if (!(sourceCountry == null || sourceCountry.equalsIgnoreCase(vacation.getSourceCountry())))
                continue;
            if (!(destCountry == null || destCountry.equalsIgnoreCase(vacation.getDestinationCountry())))
                continue;
            if (!(vacation_type == null || vacation.getVacation_type().name().equals(vacation_type.name())))
                continue;
            if (hospitalityIncluded) {
                if (!(vacation.isHospitality_Included() && vacation.getHospitality_Rank() >= minHospitalityRank))
                    continue;
            }

            if (!vacationSell.getStatus().name().equals("available"))
                continue;
            ans.add(vacationSell);
        }
        return ans;
    }

    @Override
    public boolean sendRequest(Request request) {
        try {
            String[] values = {null, request.getUsername(), "" + request.getVacationId(), PurchaseRequest.Request_Status.pending.name()};
            insertQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.class, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<PurchaseRequest> getMyRequests(String username) {
        List<String[]> results = selectQuery(tableNameEnum.PurchaseRequests_Table.name(), PurchaseRequestsfieldNameEnum.Requester_Username + "='" + username + "'");
        List<PurchaseRequest> ans = new ArrayList<>();
        for (String[] row : results) {
            List<String[]> vaction_of_request = selectQuery(tableNameEnum.Vacations_Table.name(), VacationsfieldNameEnum.Vacation_id + "='" + row[2] + "'");//get information to create vacation
            if (vaction_of_request.size() != 1)
                return null;
            String[] vacation = vaction_of_request.get(0);
            List<Flight> flightForCreateVacation = new ArrayList<>();
            List<String[]> flight_of_vacation = selectQuery(tableNameEnum.FlightsToVacations_Table.name(), FlightsToVacationsfieldNameEnum.Vacation_id + "='" + row[2] + "'");//need
            for (String[] flight : flight_of_vacation) {
                List<String[]> flightInfo = selectQuery(tableNameEnum.Flights_table.name(), FlightsfieldNameEnum.FlightID + "='" + flight[1] + "'");
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

    @Override
    public List<PurchaseRequest> getReceivedRequests(String username) {
        List<PurchaseRequest> ans = new ArrayList<>();
        List<String[]> ansfromSqlSelect = new ArrayList<>();
        String sqlcmd = "select PurchaseRequests_Table.*\n" +
                "from Vacations_Table,PurchaseRequests_Table\n" +
                "where Vacations_Table.Vacation_id = PurchaseRequests_Table.Vacation_id AND Vacations_Table.Publisher_Username='" + username + "'AND PurchaseRequests_Table.Request_Status='pending';";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlcmd)) {

            ResultSet rs = pstmt.executeQuery();
            // loop through the result set
            int nCol = rs.getMetaData().getColumnCount();
            List<String[]> table = new ArrayList<>();
            while (rs.next()) {
                String[] row = new String[nCol];
                for (int iCol = 1; iCol <= nCol; iCol++) {
                    Object obj = rs.getObject(iCol);
                    row[iCol - 1] = (obj == null) ? null : obj.toString();
                }
                table.add(row);
            }
            ansfromSqlSelect = table;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        for (String[] record : ansfromSqlSelect) {
            String[] temp_vec = selectQuery(tableNameEnum.Vacations_Table.toString(), VacationsfieldNameEnum.Vacation_id.toString() + "='" + record[2] + "'").get(0);

            List<Flight> flightForCreateVacation = new ArrayList<>();
            List<String[]> flight_of_vacation = selectQuery(tableNameEnum.FlightsToVacations_Table.name(), FlightsToVacationsfieldNameEnum.Vacation_id + "='" + temp_vec[0] + "'");
            for (String[] flight : flight_of_vacation) {
                List<String[]> flightInfo = selectQuery(tableNameEnum.Flights_table.name(), FlightsfieldNameEnum.FlightID + "='" + flight[1] + "'");
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

    @Override
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

    @Override
    public boolean rejectRequest(int requestId) {
        String[] values = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.PurchaseRequest_id + "='" + requestId + "'").get(0);
        values[3] = PurchaseRequest.Request_Status.rejected.toString();
        try {
            updateQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.class, values, PurchaseRequestsfieldNameEnum.PurchaseRequest_id + "='" + requestId + "'");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean payForVacation(int requestId, Payment payment) {
        String usernamePaying = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.PurchaseRequest_id.toString() + "='" + requestId + "'").get(0)[1];
        String[] requestInfo = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.PurchaseRequest_id.toString() + "='" + requestId + "'").get(0);
        String vacationId = requestInfo[2];
        String paymentMethod = "";
        if (payment.isVisa())
            paymentMethod = "Visa";
        else if (payment.isPaypal())
            paymentMethod = "Paypal";
        String[] values = {null, usernamePaying, vacationId, paymentMethod};
        try {
            insertQuery(tableNameEnum.Payments_Table.toString(), PaymentfieldsNameEnum.class, values);
        } catch (SQLException e) {
            return false;
        }
        String PaymentID = selectQuery(tableNameEnum.Payments_Table.toString(), PaymentfieldsNameEnum.Username.toString() + "='" + usernamePaying + "' AND " + PaymentfieldsNameEnum.VacationID + "='" + vacationId + "' AND " + PaymentfieldsNameEnum.Payment_Method + "='" + paymentMethod + "'").get(0)[0];
        List<String[]> toReject = selectQuery(tableNameEnum.PurchaseRequests_Table.toString(), PurchaseRequestsfieldNameEnum.Vacation_id + "='" + vacationId + "'");
        for (String[] tr : toReject) {
            int requestNum = Integer.parseInt(tr[0]);
            if (requestNum != requestId)
                rejectRequest(requestNum);
        }
        PayaplPayment ppp;
        VisaPayment vp;
        boolean successPay = false;
        if (payment.isPaypal()) {
            ppp = (PayaplPayment) payment;
            String[] newvalues = {PaymentID, ppp.getEmail(), ppp.getPassword()};
            try {
                insertQuery(tableNameEnum.Paypalpayments_Table.toString(), PaypalPaymentfieldsEnum.class, newvalues);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            successPay = true;
        }
        if (payment.isVisa()) {
            vp = (VisaPayment) payment;
            String[] newvalues = {PaymentID, "" + vp.getCardNumber(), "" + vp.getThreeNumbers(), vp.getDate().toString(), vp.getOwnerId(), vp.getOwnerFirstName(), vp.getOwnerLastName()};
            try {
                insertQuery(tableNameEnum.VisaPayments_Table.toString(), VisePaymentfieldsEnum.class, newvalues);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            successPay = true;
        }
        if (successPay) {
            String[] vacationInfo = selectQuery(tableNameEnum.Vacations_Table.name(), VacationsfieldNameEnum.Vacation_id + "='" + vacationId + "'").get(0);
            vacationInfo[15] = VacationSell.Vacation_Status.sold.name();
            try {
                updateQuery(tableNameEnum.Vacations_Table.name(), VacationsfieldNameEnum.class, vacationInfo, VacationsfieldNameEnum.Vacation_id + "='" + vacationId + "'");
                requestInfo[3] = PurchaseRequest.Request_Status.done.name();
                updateQuery(tableNameEnum.PurchaseRequests_Table.name(), PurchaseRequestsfieldNameEnum.class, requestInfo, PurchaseRequestsfieldNameEnum.PurchaseRequest_id.name() + "='" + requestId + "'");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }


    public boolean UsersTable_checkPassword(String Username_val, String Password_val) {
        List<String[]> result = selectQuery("Users_Table", UsersfieldNameEnum.Username + " = '" + Username_val + "'");
        if (result.size() != 1)
            return false;
        else if (result.get(0)[1].equals(Password_val) == false)
            return false;
        return true;
    }

    //todo all function below

    public static void main(String[] args) {
        Model model = new Model("Vaction4U");
//        Flight flight = new Flight("indiaair","india","hodu",LocalDate.parse("1998-11-12"),LocalDate.parse("1998-11-13"),"15:15","12:12");
//        Flight flight1 = new Flight("slovakoaair","slovakia","romania",LocalDate.parse("1998-11-13"),LocalDate.parse("1998-11-14"),"13:13","14:14");
//        List<Flight> flights = new ArrayList<>();
//        flights.add(flight);
//        flights.add(flight1);
//        Vacation vac = new Vacation("rami",LocalDate.parse("1998-11-11"),LocalDate.parse("1998-12-11"),1,1,false,"Marocow","Denemark",true,3,Vacation.Tickets_Type.Business_class,flights,Vacation.Flight_Type.One_Way,Vacation.Vacation_Type.Pleasure,true,4);
//        System.out.println(model.publishVacation(vac));
    }

    public static void TestPublishVacation() {
        Model model = new Model("Vaction4U");
        Flight flight = new Flight("indiaair", "india", "hodu", LocalDate.parse("1998-11-12"), LocalDate.parse("1998-11-13"), "15:15", "12:12");
        Flight flight1 = new Flight("slovakoaair", "slovakia", "romania", LocalDate.parse("1998-11-13"), LocalDate.parse("1998-11-14"), "13:13", "14:14");
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        flights.add(flight1);
        Vacation vac = new Vacation("rami", LocalDate.parse("1998-11-11"), LocalDate.parse("1998-12-11"), 1, 1, false, "Marocow", "Denemark", true, 3, Vacation.Tickets_Type.Business_class, flights, Vacation.Flight_Type.One_Way, Vacation.Vacation_Type.Pleasure, true, 4);
        System.out.println(model.publishVacation(vac));

    }

    public static void TestCreateUser(Model m) {
        User x1 = new User("blackjoker", "123456", LocalDate.parse("1998-09-24"), "oded", "blumenthal", "eilat", "Israel");
        User x2 = new User("michmich", "134679", LocalDate.parse("1998-05-20"), "michael", "michaelashvili", "lud", "Israel");
        User x3 = new User("eranTHEsleeper", "111111", LocalDate.parse("1999-12-05"), "eran", "taganski", "rishon", "Israel");
        User x4 = new User("theNotSoJew", "123123", LocalDate.parse("1998-08-25"), "steven", "danish", "ashdod", "Israel");
        m.createUser(x1);
        m.createUser(x2);
        m.createUser(x3);
        m.createUser(x4);
    }

    public static void TestgetUser(Model m) {
        System.out.println(m.getUser("blackjoker").toString());
        System.out.println(m.getUser("michmich").toString());
        System.out.println(m.getUser("eranTHEsleeper").toString());
        System.out.println(m.getUser("theNotSoJew").toString());
    }

    public static void TestDeleteUser(Model m) {
        System.out.println(m.deleteUser("blackjoker1"));
        System.out.println(m.deleteUser("michmich1"));
        System.out.println(m.deleteUser("eranTHEsleeper1"));
        System.out.println(m.deleteUser("blackjoker1"));
    }

    public static void TestUserExist(Model m) {
        System.out.println(m.userExist("blackjoker"));
        System.out.println(m.userExist("michmich"));
        System.out.println(m.userExist("eranTHEsleeper"));
        System.out.println(m.userExist("theNotSoJew"));
        System.out.println(m.userExist("theNotSoJew2"));
        System.out.println(m.userExist(""));
        System.out.println(m.userExist("lolThat"));
        System.out.println(m.userExist("lo552"));
    }

    public static void TestUpdateUserIInfo(Model m) {
        User x1 = new User("blackjoker1", "123456", LocalDate.parse("1998-09-24"), "oded", "blumenthal", "eilat", "Israel");
        User x2 = new User("michmich1", "134679", LocalDate.parse("1998-05-20"), "michael", "michaelashvili", "lud", "Israel");
        User x3 = new User("eranTHEsleeper1", "111111", LocalDate.parse("1999-12-05"), "eran", "taganski", "rishon", "Israel");
        User x4 = new User("theNotSoJew1", "123123", LocalDate.parse("1998-08-25"), "steven", "danish", "ashdod", "Israel");
        System.out.println(m.updateUserInfo("blackjoker1", x2));
        m.updateUserInfo("michmich", x2);
        m.updateUserInfo("eranTHEsleeper", x3);
        m.updateUserInfo("theNotSoJew", x4);
    }

    public static void TestSendRequest(Model m) {
        Request rq1 = new Request("oded", 1);
        Request rq2 = new Request("mich", 1);
        Request rq3 = new Request("eran", 1);
        Request rq4 = new Request("oded", 2);
        Request rq5 = new Request("mich", 2);
        System.out.println(m.sendRequest(rq1));
        System.out.println(m.sendRequest(rq2));
        System.out.println(m.sendRequest(rq3));
        System.out.println(m.sendRequest(rq4));
        System.out.println(m.sendRequest(rq5));
    }

    public static void TestacceptAndReject(Model m) {
//        System.out.println(m.acceptRequest(1));
//        System.out.println(m.acceptRequest(2));
//        System.out.println(m.acceptRequest(3));
//        System.out.println(m.rejectRequest(4));
        System.out.println(m.rejectRequest(6));
    }


}

