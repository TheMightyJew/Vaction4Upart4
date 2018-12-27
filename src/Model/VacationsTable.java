package Model;

import Model.Objects.Flight;
import Model.Objects.PurchaseRequest;
import Model.Objects.Vacation;
import Model.Objects.VacationSell;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VacationsTable extends AVacationdatabaseTable {

    public enum VacationsfieldNameEnum {Vacation_id, Publisher_Username, Source_Country, Destination_Country, From_Date, To_Date, Price_Per_ticket, Num_Of_Passengers, Can_Buy_less_Tickets, Tickets_Type, Vacation_Type, Flight_Type, Baggage_Limit, Lodging_Included, Lodging_Rating, Vacation_Status;}

    public VacationsTable(String databaseName) {
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
    }

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
            for (Flight flight : vacation.getFlights()) {
                insertQuery(tableNameEnum.Flights_table.name(), FlightsTable.FlightsfieldNameEnum.class, new String[]{null, flight.getSourceAirPort(), flight.getDestinationAirPort(), flight.getDepartDate().toString(), flight.getDepartHour(), flight.getLandDate().toString(), flight.getLandHour(), flight.getFlightCompany()});
                String flightID = selectQuery(tableNameEnum.Flights_table.name(), FlightsTable.FlightsfieldNameEnum.OriginAirport.name() + "='" + flight.getSourceAirPort() + "' AND " + FlightsTable.FlightsfieldNameEnum.DestinationAirport.name() + "='" + flight.getDestinationAirPort() + "' AND " + FlightsTable.FlightsfieldNameEnum.DepartureDate.name() + "='" + flight.getDepartDate().toString() + "' AND "
                        + FlightsTable.FlightsfieldNameEnum.DepartureTime.name() + "='" + flight.getDepartHour() + "' AND " + FlightsTable.FlightsfieldNameEnum.ArrivalDate.name() + "='" + flight.getLandDate().toString() + "' AND " + FlightsTable.FlightsfieldNameEnum.ArrivalTime.name() + "='" + flight.getLandHour() + "' AND " + FlightsTable.FlightsfieldNameEnum.FlightComapny.name() + "='" + flight.getFlightCompany() + "'").get(0)[0];
                insertQuery(tableNameEnum.FlightsToVacations_Table.name(), FlightsToVacationsTable.FlightsToVacationsfieldNameEnum.class, new String[]{String.valueOf(vacationID), String.valueOf(flightID)});
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<VacationSell> getVacations(String username, String flightCompany, LocalDate fromDate, LocalDate toDate, boolean baggage, Integer baggageMin, Integer ticketsNum, Vacation.Tickets_Type tickets_type, Integer maxPricePerTicket, String sourceCountry, String destCountry, Vacation.Vacation_Type vacation_type, Vacation.Flight_Type flight_type, boolean hospitalityIncluded, Integer minHospitalityRank) {
        String sql = "SELECT Vacations_Table.Vacation_id,Vacations_Table.Publisher_Username,Vacations_Table.Price_Per_ticket,Vacations_Table.Num_Of_Passengers,Vacations_Table.Can_Buy_less_Tickets,Vacations_Table.Source_Country,Vacations_Table.Destination_Country,Vacations_Table.From_Date,Vacations_Table.To_Date,Vacations_Table.Baggage_Limit,Vacations_Table.Tickets_Type,Vacations_Table.Flight_Type,Vacations_Table.Vacation_Type,Vacations_Table.Lodging_Included,Vacations_Table.Lodging_Rating,Vacations_Table.Vacation_Status,Flights_table.FlightComapny,Flights_table.DepartureDate,Flights_table.DepartureTime,Flights_table.ArrivalDate,Flights_table.ArrivalTime,Flights_table.OriginAirport,Flights_table.DestinationAirport\n" +
                "FROM ((FlightsToVacations_Table \n" +
                "INNER JOIN Vacations_Table ON FlightsToVacations_Table.Vacation_id = Vacations_Table.Vacation_id)\n" +
                "INNER JOIN Flights_table ON Flights_table.FlightID = FlightsToVacations_Table.Flight_id)\n" +
                "ORDER BY Vacations_Table.Vacation_id ASC;";
        List<VacationSell> optinalVacation = new ArrayList<>();
        List<VacationSell> ans = new ArrayList<>();
        List<String[]> table = new ArrayList<>();
        table = specificSelectQuery(sql);
        if (table == null)
            return null;
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
                "WHERE " + PurchaseRequestsTable.PurchaseRequestsfieldNameEnum.Requester_Username.name() + "<>'" + username + "';";
        List<String[]> userRequests;
        userRequests = specificSelectQuery(sql1);
        if (userRequests == null)
            return null;
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
            if (!(ticketsNum == null || vacation.getTickets_Quantity() == ticketsNum || (vacation.isCanBuyLess() && vacation.getTickets_Quantity() >= ticketsNum)))
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

}
