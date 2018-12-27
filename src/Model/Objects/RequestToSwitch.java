package Model.Objects;

public class RequestToSwitch {

    private int wantedVacation;
    private int offeredVacation;

    public RequestToSwitch(int wantedVacation, int offeredVacation) {
        this.wantedVacation = wantedVacation;
        this.offeredVacation = offeredVacation;
    }

    public int getWantedVacation() {
        return wantedVacation;
    }

    public int getOfferedVacation() {
        return offeredVacation;
    }
}
