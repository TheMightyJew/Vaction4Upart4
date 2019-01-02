package Model.Requests;

import Model.Objects.VacationSell;

public class TradeARequest extends ARequest {

    private VacationSell wanted;
    private VacationSell offer;

    public TradeARequest(int id, Request_Status status, VacationSell wanted, VacationSell offer) {
        super(id, status);
        this.wanted = wanted;
        this.offer = offer;
    }

    public VacationSell getWanted() {
        return wanted;
    }

    public VacationSell getOffer() {
        return offer;
    }


}
