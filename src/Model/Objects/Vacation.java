package Model.Objects;

import java.time.LocalDate;
import java.util.List;

public class Vacation {

    public static enum Tickets_Type{Economy_class,Business_class};
    public static enum Vacation_Type{Pleasure,Business};
    public static enum Flight_Type{One_Way,Round_Trip};

    private String seller_username;//
    private LocalDate fromDate;//
    private LocalDate toDate;//
    private int price_Per_Ticket;//
    private int tickets_Quantity;//
    private boolean canBuyLess;//true\false
    private String sourceCountry;//
    private String destinationCountry;//
    private boolean baggage_Included;//if baggageLimit>0 -> true, else false
    private Integer baggageLimit;
    private Tickets_Type ticketsType;//
    private List<Flight> flights;//
    private Flight_Type flight_Type;//
    private Vacation_Type vacation_type;//
    private boolean hospitality_Included;//
    private Integer hospitality_Rank;//between 1 to 5

    public Vacation(String seller_username, LocalDate fromDate, LocalDate toDate, int price_Per_Ticket, int tickets_Quantity, boolean canBuyLess, String sourceCountry, String destinationCountry, boolean baggage_Included, Integer baggageLimit, Tickets_Type ticketsType, List<Flight> flights, Flight_Type flight_Type, Vacation_Type vacation_type, boolean hospitality_Included, Integer hospitality_Rank) {
        this.seller_username = seller_username;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.price_Per_Ticket = price_Per_Ticket;
        this.tickets_Quantity = tickets_Quantity;
        this.canBuyLess = canBuyLess;
        this.sourceCountry = sourceCountry;
        this.destinationCountry = destinationCountry;
        this.baggage_Included = baggage_Included;
        this.baggageLimit = baggageLimit;
        this.ticketsType = ticketsType;
        this.flights = flights;
        this.flight_Type = flight_Type;
        this.vacation_type = vacation_type;
        this.hospitality_Included = hospitality_Included;
        this.hospitality_Rank = hospitality_Rank;
    }

    public String getSeller_username() {
        return seller_username;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public int getPrice_Per_Ticket() {
        return price_Per_Ticket;
    }

    public int getTickets_Quantity() {
        return tickets_Quantity;
    }

    public boolean isCanBuyLess() {
        return canBuyLess;
    }

    public String getSourceCountry() {
        return sourceCountry;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public boolean isBaggage_Included() {
        return baggage_Included;
    }

    public Integer getBaggageLimit() {
        return baggageLimit;
    }

    public Tickets_Type getTicketsType() {
        return ticketsType;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Flight_Type getFlight_Type() {
        return flight_Type;
    }

    public Vacation_Type getVacation_type() {
        return vacation_type;
    }

    public boolean isHospitality_Included() {
        return hospitality_Included;
    }

    public Integer getHospitality_Rank() {
        return hospitality_Rank;
    }
}
