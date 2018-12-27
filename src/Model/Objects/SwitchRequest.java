package Model.Objects;

public class SwitchRequest {
    private int id;
    private VacationSell wanted;
    private VacationSell offer;
    private PurchaseRequest.Request_Status status;

    public SwitchRequest(int id, VacationSell wanted, VacationSell offer, PurchaseRequest.Request_Status status) {
        this.id = id;
        this.wanted = wanted;
        this.offer = offer;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public VacationSell getWanted() {
        return wanted;
    }

    public VacationSell getOffer() {
        return offer;
    }

    public PurchaseRequest.Request_Status getStatus() {
        return status;
    }
}
