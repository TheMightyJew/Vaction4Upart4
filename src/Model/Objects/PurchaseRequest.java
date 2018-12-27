package Model.Objects;

public class PurchaseRequest {
    public enum Request_Status{rejected,accepted,pending,done};

    private int id;
    private String username;
    private VacationSell vacationSell;
    private Request_Status status;

    public PurchaseRequest(int id, String username, VacationSell vacationSell, Request_Status status) {
        this.id = id;
        this.username = username;
        this.vacationSell = vacationSell;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public VacationSell getVacationSell() {
        return vacationSell;
    }

    public Request_Status getStatus() {
        return status;
    }
}
