package Model;


import Model.Objects.*;
import Model.Requests.PurchaseARequest;
import Model.Requests.PurchaseRequestData;

import java.time.LocalDate;
import java.util.List;

public interface IModel {
    //Users Actions
    //String[] getUser(String Username_val);
    boolean deleteUser(String username);

    boolean userExist(String username);

    //update top functions to this functions:
    boolean createUser(User user);
    User getUser(String username_val);
    boolean updateUserInfo(String username, User user);
    //new functions:

    /**
     * This functions should put the vacation in the database with unique id.
     * need to apply: VacationSell.Vacation_Status=VacationSell.Vacation_Status.available
     *
     * @param vacation - vacation info from user that want to sell
     * @return if publish succeeded.
     */
    boolean publishVacation(Vacation vacation);

    /**
     * Value will be null if user not requested the specific limitation(boolean will not)
     *
     * @param flightCompany       -
     * @param fromDate            - The vacation first flight should be from this date or after
     * @param toDate              - The vacation last flight should be from this date or before
     * @param baggage             - If baggage included
     * @param baggageMin          - The vacation
     * @param ticketsNum          - The number of tickets the user want to be possible to purchase from the vacation
     * @param tickets_type        -
     * @param maxPricePerTicket   - The vacation
     *
     * @param sourceCountry       -
     * @param destCountry         -
     * @param vacation_type       -
     * @param hospitalityIncluded -
     * @param minHospitalityRank  -
     * @return list of vacationsell where status=available;
     */
    List<VacationSell> getVacations(String username,String flightCompany, LocalDate fromDate, LocalDate toDate, boolean baggage, Integer baggageMin, Integer ticketsNum, Vacation.Tickets_Type tickets_type, Integer maxPricePerTicket, String sourceCountry, String destCountry, Vacation.Vacation_Type vacation_type,Vacation.Flight_Type flight_type, boolean hospitalityIncluded, Integer minHospitalityRank);

    /**
     * This functions should put the purchaseRequestData in the database with unique id
     * need to apply: PurchaseARequest.Request_Status=PurchaseARequest.Request_Status.pending
     *
     * @param purchaseRequestData - purchaseRequestData from a user that want to buy certain vacation
     * @return if purchaseRequestData application succeeded.
     */
    boolean sendRequest(PurchaseRequestData purchaseRequestData);

    /**
     * @param username -
     * @return the requests that the given username's user sent to other users
     */
    List<PurchaseARequest> getMyPurchaseRequests(String username);

    /**
     * get the request that username user received from other users.
     *
     * @param username
     * @return
     */
    List<PurchaseARequest> getReceivedPurchaseRequests(String username);

    /**
     * @param requestId
     */
    boolean acceptPurchaseRequest(int requestId);

    /**
     * @param requestId
     */
    boolean rejectPurchaseRequest(int requestId);

    /**
     * save payment to database using payment.tostring function.
     *
     * @param requestId
     * @param payment
     * @return
     */
//    boolean payForVacation(int requestId, Payment payment);
}
