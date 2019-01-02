package Model.Database;

import Model.Requests.ARequest;
import Model.Requests.ARequestData;


import java.util.List;

public abstract class ARequestTable extends AVacationdatabaseTable {

    public abstract boolean sendRequest(ARequestData requestData);

    public abstract List<ARequest> getMyRequests(String username);

    public abstract List<ARequest> getReceivedRequests(String username);

    public abstract boolean acceptRequest(int requestId);

    public abstract boolean rejectRequest(int requestId);
}
