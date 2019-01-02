package Model.Requests;

public class TradeRequestData extends ARequestData{

    private int wantedVacation;
    private int offeredVacation;

    public TradeRequestData(int wantedVacation, int offeredVacation) {
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
