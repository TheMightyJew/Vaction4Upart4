package Model.Requests;

import Model.Objects.VacationSell;

public class PurchaseARequest extends ARequest {

    private String username;
    private VacationSell vacationSell;

    public PurchaseARequest(int id, String username, VacationSell vacationSell, Request_Status status) {
        super(id, status);
        this.username = username;
        this.vacationSell = vacationSell;
    }

    public String getUsername() {
        return username;
    }

    public VacationSell getVacationSell() {
        return vacationSell;
    }

}
