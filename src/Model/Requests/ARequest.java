package Model.Requests;

public abstract class ARequest {
    public enum Request_Status {rejected, accepted, pending, done}

    private int id;
    private Request_Status status;

    public ARequest(int id, Request_Status status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Request_Status getStatus() {
        return status;
    }

}
