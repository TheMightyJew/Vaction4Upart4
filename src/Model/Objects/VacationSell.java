package Model.Objects;

public class VacationSell {

    public static enum Vacation_Status{available,sold,out_of_date};

    private int id;
    private Vacation vacation;
    private Vacation_Status status;

    public VacationSell(int id, Vacation vacation, Vacation_Status status) {
        this.id = id;
        this.vacation = vacation;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public Vacation_Status getStatus() {
        return status;
    }
}
